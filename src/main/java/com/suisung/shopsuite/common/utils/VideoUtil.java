package com.suisung.shopsuite.common.utils;


import com.coremedia.iso.IsoFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoUtil {

    private static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    public static List<String> videoAllowFiles = new ArrayList<String>() {{
        add("flv");
        add("swf");
        add("mkv");
        add("avi");
        add("rm");
        add("rmvb");
        add("mpeg");
        add("mpg");
        add("ogg");
        add("ogv");
        add("mov");
        add("wmv");
        add("mp4");
        add("webm");
        add("mp3");
        add("wav");
        add("mid");
    }};

    /**
     * 得到语音或视频文件时长,单位秒 并格式化
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFormatDuration(String filePath) throws IOException {
        return getVideoFormat(getDuration(filePath));
    }

    /**
     * 获取视频文件的播放长度(mp4、mov格式)
     *
     * @param videoPath
     * @return 单位为毫秒
     */
    public static long getMp4Duration(String videoPath) throws IOException {
        IsoFile isoFile = new IsoFile(videoPath);
        long lengthInSeconds =
                isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                        isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        return lengthInSeconds;
    }


    /**
     * 得到语音或视频文件时长,单位秒
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath) throws IOException {
        String format = getVideoFormat(filePath);
        long result = 0;
        if ("wav".equals(format)) {
            result = AudioUtil.getDuration(filePath).intValue();
        } else if ("mp3".equals(format)) {
            result = AudioUtil.getMp3Duration(filePath).intValue();
        } else if ("m4a".equals(format)) {
            result = getMp4Duration(filePath);
        } else if ("mov".equals(format)) {
            result = getMp4Duration(filePath);
        } else if ("mp4".equals(format)) {
            result = getMp4Duration(filePath);
        }

        return result;
    }

    /**
     * 得到语音或视频文件时长,单位秒
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath, String format) throws IOException {
        long result = 0;
        if ("wav".equals(format)) {
            result = AudioUtil.getDuration(filePath).intValue();
        } else if ("mp3".equals(format)) {
            result = AudioUtil.getMp3Duration(filePath).intValue();
        } else if ("m4a".equals(format)) {
            result = getMp4Duration(filePath);
        } else if ("mov".equals(format)) {
            result = getMp4Duration(filePath);
        } else if ("mp4".equals(format)) {
            result = getMp4Duration(filePath);
        }

        return result;
    }


    /**
     * 得到文件格式
     *
     * @param path
     * @return
     */
    public static String getVideoFormat(String path) {
        return path.toLowerCase().substring(path.toLowerCase().lastIndexOf(".") + 1);
    }


    /**
     * 截取视频第一帧
     *
     * @param video 视频文件
     * @param image 图片文件
     * @param time  第几帧
     * @param size  截图尺寸
     */
    public static boolean getVideoCover(String video, String image, Integer time, String size) {
        boolean flag = true;

        //临时视频路径，生成截图后删除
        //String command = "D:\\tools\\ffmpeg-2022-07-10-git-846488cca8-full_build\\bin\\ffmpeg.exe -ss " + time.toString() + " -i " + video + " -y -f mjpeg -t 0.001 -s " + size + " " + image;
        String command = "ffmpeg -ss " + time.toString() + " -i " + video + " -y -f mjpeg -t 0.001 -s " + size + " " + image;
        //String command = ffmpeg + " -i "+ inFile +" -vframes 1 -y -f image2 -t 1 -s " + size  + " " + outFile;

        try {
            Process process;

            /*
            //process = Runtime.getRuntime().exec(new String[]{ "/bin/sh", "/c", command});
            process = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", command});
            //取得命令结果的输出流
            InputStream inputStream = process.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            //知道读完为止
            while ((line=br.readLine())!=null){
                System.out.println(line);
            }

             */

            process = Runtime.getRuntime().exec(command);
            new InputStreamRunnable(process.getErrorStream(), "Error").start();
            new InputStreamRunnable(process.getInputStream(), "Info").start();
            boolean code = process.waitFor(30, TimeUnit.MINUTES);
            process.destroy();
            /*
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("ffmpeg cmd:" + line);
            }
             */
            /*
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
             */
        } catch (Exception e) {
            logger.error("截取视频第一帧信息异常！" + e.getMessage(), e);
            flag = false;
        }

        return flag;
    }

    public static String getVideoFormat(long time) {
        if (time == 0) return "";
        int temp = (int) time;
        int hh = temp / 3600;
        int mm = (temp % 3600) / 60;
        int ss = (temp % 3600) % 60;

        return hh != 0 ? ((hh < 10 ? ("0" + hh) : hh) + ":") : "" +
                (mm < 10 ? ("0" + mm) : mm) + ":" +
                (ss < 10 ? ("0" + ss) : ss);
    }
}

class InputStreamRunnable extends Thread {
    BufferedReader bReader = null;
    String type = null;

    public InputStreamRunnable(InputStream is, String _type) {
        try {
            bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is), "UTF-8"));
            type = _type;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        String line;
        int lineNum = 0;
        try {
            while ((line = bReader.readLine()) != null) {
                lineNum++;
                System.out.println(type + ":" + line);
            }
            bReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}