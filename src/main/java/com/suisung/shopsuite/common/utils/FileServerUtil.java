package com.suisung.shopsuite.common.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传下载工具类
 *
 * @author Xinze
 * @since 2018-12-14 08:38:53
 */
public class FileServerUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileServerUtil.class);

    // 除 text/* 外也需要设置输出编码的 content-type
    private final static List<String> SET_CHARSET_CONTENT_TYPES = Arrays.asList(
            "application/json",
            "application/javascript"
    );

    /**
     * 上传文件
     *
     * @param file      MultipartFile
     * @param directory 文件保存的目录
     * @param uuidName  是否用uuid命名
     * @return File
     */
    public static File upload(MultipartFile file, String directory, boolean uuidName)
            throws IOException, IllegalStateException {
        File outFile = getUploadFile(file.getOriginalFilename(), directory, uuidName);
        if (!outFile.getParentFile().exists()) {
            if (!outFile.getParentFile().mkdirs()) {
                throw new RuntimeException("make directory fail");
            }
        }
        file.transferTo(outFile);
        return outFile;
    }

    /**
     * 上传base64格式文件
     *
     * @param base64    base64编码字符
     * @param fileName  文件名称, 为空使用uuid命名
     * @param directory 文件保存的目录
     * @return File
     */
    public static File upload(String base64, String fileName, String directory)
            throws FileNotFoundException, IORuntimeException {
        if (StrUtil.isBlank(base64) || !base64.startsWith("data:image/") || !base64.contains(";base64,")) {
            throw new RuntimeException("base64 data error");
        }
        String suffix = "." + base64.substring(11, base64.indexOf(";"));  // 获取文件后缀
        boolean uuidName = StrUtil.isBlank(fileName);
        File outFile = getUploadFile(uuidName ? suffix : fileName, directory, uuidName);
        byte[] bytes = Base64.getDecoder().decode(base64.substring(base64.indexOf(";") + 8).getBytes());
        IoUtil.write(new FileOutputStream(outFile), true, bytes);
        return outFile;
    }

    /**
     * 获取上传文件位置
     *
     * @param name      文件名称
     * @param directory 上传目录
     * @param uuidName  是否使用uuid命名
     * @return File
     */
    public static File getUploadFile(String name, String directory, boolean uuidName) {
        // 当前日期作为上传子目录
        String dir = new SimpleDateFormat("yyyyMMdd/").format(new Date());
        // 获取文件后缀
        String suffix = (name == null || !name.contains(".")) ? "" : name.substring(name.lastIndexOf("."));
        // 使用uuid命名
        if (uuidName || name == null) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            return new File(directory, dir + uuid + suffix);
        }
        // 使用原名称, 存在相同则加(1)
        File file = new File(directory, dir + name);
        String prefix = StrUtil.removeSuffix(name, suffix);
        int sameSize = 2;
        while (file.exists()) {
            file = new File(directory, dir + prefix + "(" + sameSize + ")" + suffix);
            sameSize++;
        }
        return file;
    }

    /**
     * 查看文件, 支持断点续传
     *
     * @param file       文件
     * @param pdfDir     office转pdf输出目录
     * @param officeHome openOffice安装目录
     * @param response   HttpServletResponse
     * @param request    HttpServletRequest
     */
    public static void preview(File file, String pdfDir, String officeHome,
                               HttpServletResponse response, HttpServletRequest request) {
        preview(file, false, null, pdfDir, officeHome, response, request);
    }

    /**
     * 查看文件, 支持断点续传
     *
     * @param file          文件
     * @param forceDownload 是否强制下载
     * @param fileName      强制下载的文件名称
     * @param pdfDir        office转pdf输出目录
     * @param officeHome    openOffice安装目录
     * @param response      HttpServletResponse
     * @param request       HttpServletRequest
     */
    public static void preview(File file, boolean forceDownload, String fileName, String pdfDir, String officeHome,
                               HttpServletResponse response, HttpServletRequest request) {
        CommonUtil.addCrossHeaders(response);
        if (file == null || !file.exists()) {
            outNotFund(response);
            return;
        }
        if (forceDownload) {
            setDownloadHeader(response, StrUtil.isBlank(fileName) ? file.getName() : fileName);
        } else {
            // office转pdf预览
            if (OpenOfficeUtil.canConverter(file.getName())) {
                File pdfFile = OpenOfficeUtil.converterToPDF(file.getAbsolutePath(), pdfDir, officeHome);
                if (pdfFile != null) {
                    file = pdfFile;
                }
            }
            // 获取文件类型
            String contentType = getContentType(file);
            if (contentType != null) {
                response.setContentType(contentType);
                // 设置编码
                if (contentType.startsWith("text/") || SET_CHARSET_CONTENT_TYPES.contains(contentType)) {
                    try {
                        String charset = JChardetFacadeUtil.detectCodepage(file.toURI().toURL());
                        if (charset != null) {
                            response.setCharacterEncoding(charset);
                        }
                    } catch (MalformedURLException e) {
                        logger.error("获取文件编码失败", e);
                    }
                }
            } else {
                setDownloadHeader(response, file.getName());
            }
        }
        response.setHeader("Cache-Control", "public");
        output(file, response, request);
    }

    /**
     * 查看缩略图
     *
     * @param file      原文件
     * @param thumbnail 缩略图文件
     * @param size      缩略图文件的最大值(kb)
     * @param response  HttpServletResponse
     * @param request   HttpServletRequest
     */
    public static void previewThumbnail(File file, File thumbnail, Integer size,
                                        HttpServletResponse response, HttpServletRequest request) {
        // 如果是图片并且缩略图不存在则生成
        if (!thumbnail.exists() && isImage(file)) {
            long fileSize = file.length();
            if ((fileSize / 1024) > size) {
                try {
                    if (thumbnail.getParentFile().mkdirs()) {
                        ImgUtil.scale(file, thumbnail, size / (fileSize / 1024f));
                        if (thumbnail.exists() && thumbnail.length() > file.length()) {
                            FileUtil.copy(file, thumbnail, true);
                        }
                    }
                } catch (Exception e) {
                    logger.error("生成缩略图失败", e);
                }
            } else {
                preview(file, null, null, response, request);
                return;
            }
        }
        preview(thumbnail.exists() ? thumbnail : file, null, null, response, request);
    }

    /**
     * 输出文件流, 支持断点续传
     *
     * @param file     文件
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     */
    public static void output(File file, HttpServletResponse response, HttpServletRequest request) {
        long length = file.length();  // 文件总大小
        long start = 0, to = length - 1;  // 开始读取位置, 结束读取位置
        long lastModified = file.lastModified();  // 文件修改时间
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", "\"" + length + "-" + lastModified + "\"");
        response.setHeader("Last-Modified", new Date(lastModified).toString());
        String range = request.getHeader("Range");
        if (range != null) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String[] ranges = range.replace("bytes=", "").split("-");
            start = Long.parseLong(ranges[0].trim());
            if (ranges.length > 1) {
                to = Long.parseLong(ranges[1].trim());
            }
            response.setHeader("Content-Range", "bytes " + start + "-" + to + "/" + length);
        }
        response.setHeader("Content-Length", String.valueOf(to - start + 1));
        try {
            output(file, response.getOutputStream(), 2048, start, to);
        } catch (IOException e) {
            logger.error("输出文件失败", e);
        }
    }

    /**
     * 输出文件流
     *
     * @param file 文件
     * @param os   输出流
     */
    public static void output(File file, OutputStream os) {
        output(file, os, null);
    }

    /**
     * 输出文件流
     *
     * @param file 文件
     * @param os   输出流
     * @param size 读取缓冲区大小
     */
    public static void output(File file, OutputStream os, Integer size) {
        output(file, os, size, null, null);
    }

    /**
     * 输出文件流, 支持分片
     *
     * @param file  文件
     * @param os    输出流
     * @param size  读取缓冲区大小
     * @param start 开始位置
     * @param to    结束位置
     */
    public static void output(File file, OutputStream os, Integer size, Long start, Long to) {
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            if (start != null) {
                long skip = is.skip(start);
                if (skip < start) {
                    System.out.println("ERROR: skip fail[ skipped=" + skip + ", start= " + start + " ]");
                }
                to = to - start + 1;
            }
            byte[] bytes = new byte[size == null ? 2048 : size];
            int len;
            if (to == null) {
                while ((len = is.read(bytes)) != -1) {
                    os.write(bytes, 0, len);
                }
            } else {
                while (to > 0 && (len = is.read(bytes)) != -1) {
                    os.write(bytes, 0, to < len ? (int) ((long) to) : len);
                    to -= len;
                }
            }
            os.flush();
        } catch (IOException ignored) {
        } catch (Exception e) {
            logger.error("输出文件流失败", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignored) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return String
     */
    public static String getContentType(File file) {
        String contentType = null;
        if (file.exists()) {
            try {
                contentType = new Tika().detect(file);
            } catch (IOException e) {
                logger.error("获取文件类型失败", e);
            }
        }
        return contentType;
    }

    /**
     * 判断文件是否是图片类型
     *
     * @param file 文件
     * @return boolean
     */
    public static boolean isImage(File file) {
        return isImage(getContentType(file));
    }

    /**
     * 判断文件是否是图片类型
     *
     * @param contentType 文件类型
     * @return boolean
     */
    public static boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }

    /**
     * 设置下载文件的header
     *
     * @param response HttpServletResponse
     * @param fileName 文件名称
     */
    public static void setDownloadHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/force-download");
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("获取文件名称", e);
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
    }

    /**
     * 输出404错误页面
     *
     * @param response HttpServletResponse
     */
    public static void outNotFund(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        outMessage("404 Not Found", null, response);
    }

    /**
     * 输出错误页面
     *
     * @param title    标题
     * @param message  内容
     * @param response HttpServletResponse
     */
    public static void outMessage(String title, String message, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write("<!doctype html>");
            writer.write("<title>" + title + "</title>");
            writer.write("<h1 style=\"text-align: center\">" + title + "</h1>");
            if (message != null) {
                writer.write(message);
            }
            writer.write("<hr/><p style=\"text-align: center\">Xinze File Server</p>");
            writer.flush();
        } catch (IOException e) {
            logger.error("输出错误页面失败", e);
        }
    }

}
