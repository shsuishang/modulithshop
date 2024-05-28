package com.suisung.shopsuite.sys.controller.front;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.consts.ConstantUpload;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.UploadDto;
import com.suisung.shopsuite.common.utils.*;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.sys.model.entity.MaterialBase;
import com.suisung.shopsuite.sys.model.req.MaterialBaseUploadReq;
import com.suisung.shopsuite.sys.model.res.UploadRes;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.MaterialBaseService;
import com.suisung.shopsuite.sys.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;
import static com.suisung.shopsuite.common.utils.UploadUtil.*;

/**
 * <p>
 * 图片上传 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "图片上传")
@RestController
@RequestMapping("/front/sys/upload")
public class UploadController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private MaterialBaseService materialBaseService;

    @Autowired
    private OssService ossService;

    @Autowired
    private ConfigBaseService configBaseService;

    @ApiOperation("上传文件")
    @PostMapping("/index")
    public CommonRes<UploadRes> upload(MultipartFile upfile, MaterialBaseUploadReq materialBaseUploadReq, HttpServletRequest request) {
        UploadRes uploadRes = null;
        try {
            File upload;
            String dir;
            InputStream inputStream = upfile.getInputStream();

            String originalFilename = upfile.getOriginalFilename();
            // 获取文件后缀
            String suffix = (originalFilename == null || !originalFilename.contains(".")) ? "" : originalFilename.substring(originalFilename.lastIndexOf("."));
            suffix = suffix.replace(".", "");
            
            String imageAllowExt = configBaseService.getConfig("upload_image_ext", "");
            String[] imageAllowExtList = imageAllowExt.split(",");

            String vedioAllowExt = configBaseService.getConfig("upload_video_ext", "");
            String[] vedioAllowExtList = vedioAllowExt.split(",");

            String fileAllowExt = configBaseService.getConfig("upload_file_ext", "");
            String[] fileAllowExtList = fileAllowExt.split(",");

            String[] allowExtList = ArrayUtil.addAll(imageAllowExtList, vedioAllowExtList, fileAllowExtList);

            if (!ArrayUtil.contains(allowExtList, suffix)) {
                throw new BusinessException(String.format(__("允许上传格式为：【%s】"), StringUtils.join(allowExtList, ",")));
            }

            switch (materialBaseUploadReq.getMaterialType()) {
                case "image":
                    dir = getUploadImageDir();
                    upload = UploadUtil.uploadImg(upfile, dir, ConstantUpload.UUID_NAME);
                    break;
                case "video":
                    dir = getUploadVideoDir();
                    upload = UploadUtil.uploadVideo(upfile, dir, ConstantUpload.UUID_NAME);
                    break;
                case "document":
                    dir = getUploadFileDir();
                    upload = UploadUtil.uploadFile(upfile, dir, ConstantUpload.UUID_NAME);
                    break;
                default:
                    dir = getUploadFileDir();
                    upload = UploadUtil.upload(upfile, dir, ConstantUpload.UUID_NAME);
                    break;
            }

            String absolutePath = upload.getAbsolutePath();
            String path = absolutePath.replace(File.separator, "/").substring(getUploadBaseDir().length() + 1);
            String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/index");
            requestURL = ConstantUpload.WEB_URL + "/front/sys/upload";

            String originalName = upfile.getOriginalFilename();

            MaterialBase result = new MaterialBase();
            result.setUserId(ContextUtil.getLoginUserId());
            result.setMaterialName(StrUtil.isBlank(originalName) ? upload.getName() : originalName);
            result.setMaterialAlt(result.getMaterialName());
            result.setMaterialSize(upload.length());
            result.setMaterialPath(path);
            result.setGalleryId(materialBaseUploadReq.getGalleryId());
            result.setMaterialType(materialBaseUploadReq.getMaterialType());
            result.setMaterialUrl(requestURL + "/" + path);
            String contentType = UploadUtil.getContentType(upload);
            result.setMaterialMimeType(contentType);

            if (UploadUtil.isImage(contentType)) {
                //result.setThumbnail(requestURL + "/thumbnail/" + path);
            }

            //result.setDownloadUrl(requestURL + "/download/" + path);

            uploadRes = new UploadRes();
            uploadRes.setUserId(ContextUtil.getLoginUserId());
            uploadRes.setFileName(result.getMaterialName());
            uploadRes.setFilePath(result.getMaterialPath());
            uploadRes.setFileSize(result.getMaterialSize());
            uploadRes.setFileType(result.getMaterialType());
            uploadRes.setMimeType(result.getMaterialMimeType());
            uploadRes.setFileUrl(result.getMaterialUrl());

            //oss文件上传网址
            Integer uploadType = configBaseService.getConfig("upload_type", 0);
            if (uploadType.equals(1) || uploadType.equals(2)) {
                //针对证书上传，特别处理。
                if (CheckUtil.isNotEmpty(materialBaseUploadReq.getMaterialKey())) {
                    if (materialBaseUploadReq.getMaterialKey().equals("wechat_pay_apiclient_cert")
                            || materialBaseUploadReq.getMaterialKey().equals("wechat_pay_apiclient_key")
                            || materialBaseUploadReq.getMaterialKey().equals("alipay_app_cert_path")
                            || materialBaseUploadReq.getMaterialKey().equals("alipay_cert_path")
                            || materialBaseUploadReq.getMaterialKey().equals("alipay_root_cert_path")) {
                        uploadRes.setFileUrl(absolutePath);
                    }
                } else {
                    UploadDto uploadDto = new UploadDto();
                    uploadDto.setUploadType(uploadType);
                    uploadDto.setFile(upload);
                    uploadDto.setMultipartfile(upfile);
                    uploadDto.setInputStream(inputStream);
                    uploadDto.setMaterialType(materialBaseUploadReq.getMaterialType());

                    String url = ThirdUtil.upload(uploadDto);
                    //String url = ossService.ossUploadObject(upfile, inputStream, materialBaseUploadReq.getMaterialType());
                    uploadRes.setFileUrl(url);
                    result.setMaterialUrl(url);
                }
            }

            materialBaseService.add(result);

            uploadRes.setUrl(uploadRes.getFileUrl());

            return success(uploadRes);

        } catch (Exception e) {
            LogUtil.error(ConstantLog.UPLOAD, e);
            return fail("上传失败", uploadRes).setError(e.toString());
        }
    }

    @ApiOperation("上传base64文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64", required = true, dataType = "String"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String")
    })
    @PostMapping("/base64")
    public CommonRes<UploadRes> uploadBase64(String base64, String fileName, HttpServletRequest request) {
        MaterialBase result = null;
        UploadRes uploadRes = null;
        try {
            String dir = getUploadBaseDir();
            File upload = UploadUtil.upload(base64, fileName, getUploadBaseDir());
            String path = upload.getAbsolutePath().substring(dir.length()).replace("\\", "/");
            String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/upload/base64");
            requestURL = ConstantUpload.WEB_URL + "/front/sys/upload";

            result = new MaterialBase();
            result.setUserId(ContextUtil.getLoginUserId());
            result.setMaterialName(StrUtil.isBlank(fileName) ? upload.getName() : fileName);
            result.setMaterialSize(upload.length());
            result.setMaterialPath(path);
            result.setMaterialUrl(requestURL + path);
            //result.setThumbnail(UploadUtil.isImage(upload) ? (requestURL + "/thumbnail" + path) : null);
            materialBaseService.add(result);

            uploadRes = new UploadRes();
            uploadRes.setUserId(ContextUtil.getLoginUser().getUserId());
            uploadRes.setFileName(result.getMaterialName());
            uploadRes.setFilePath(result.getMaterialPath());
            uploadRes.setFileSize(result.getMaterialSize());
            uploadRes.setFileType(result.getMaterialType());
            uploadRes.setMimeType(result.getMaterialMimeType());
            uploadRes.setFileUrl(result.getMaterialUrl());

            uploadRes.setUrl(uploadRes.getFileUrl());
            return success(uploadRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.UPLOAD, e);
            return fail("上传失败", uploadRes).setError(e.toString());
        }
    }

    @ApiOperation("查看原文件")
    @GetMapping("/{type}/{dir}/{name:.+}")
    public void preview(@PathVariable("type") String type, @PathVariable("dir") String dir, @PathVariable("name") String name,
                        HttpServletResponse response, HttpServletRequest request) {
        File file = new File(getUploadBaseDir(), "/" + type + "/" + dir + "/" + name);
        UploadUtil.preview(file, getPdfOutDir(), ConstantUpload.OPEN_OFFICE_HOME, response, request);
    }

    @ApiOperation("下载原文件")
    @GetMapping("/download/{type}/{dir}/{name:.+}")
    public void download(@PathVariable("type") String type, @PathVariable("dir") String dir, @PathVariable("name") String name,
                         HttpServletResponse response, HttpServletRequest request) {
        String path = "/" + type + "/" + dir + "/" + name;
        MaterialBase record = materialBaseService.get(path);
        File file = new File(getUploadBaseDir(), path);
        String fileName = record == null ? file.getName() : record.getMaterialName();
        UploadUtil.preview(file, true, fileName, null, null, response, request);
    }

    @ApiOperation("查看缩略图")
    @GetMapping("/thumbnail/{type}/{dir}/{name:.+}")
    public void thumbnail(@PathVariable("type") String type, @PathVariable("dir") String dir, @PathVariable("name") String name,
                          HttpServletResponse response, HttpServletRequest request) {
        File file = new File(getUploadBaseDir(), "/" + type + "/" + dir + "/" + name);
        File thumbnail = new File(getUploadSmDir(), "/" + type + "/" + dir + "/" + name);
        UploadUtil.previewThumbnail(file, thumbnail, ConstantUpload.THUMBNAIL_SIZE, response, request);
    }
}

