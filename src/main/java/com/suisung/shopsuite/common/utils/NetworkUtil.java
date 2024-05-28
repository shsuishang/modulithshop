package com.suisung.shopsuite.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {

    /**
     * 网络图片转输入流
     *
     * @param fileUrl
     * @return
     */
    public static InputStream urlToInputSteam(String fileUrl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        InputStream stream = null;
        try {
            // 打开服务器图片路径
            URL url = new URL(fileUrl);
            //创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");  // 提交模式
            connection.setConnectTimeout(10 * 1000);  //连接超时5秒
            connection.setReadTimeout(10 * 1000);   //读取超时10秒

            is = connection.getInputStream();
            int file_size = connection.getContentLength();  //文件大小
            byte[] result = new byte[file_size];
            int readCount = 0;
            while (readCount < file_size) {
                readCount += is.read(result, readCount, file_size - readCount);
            }
            stream = new ByteArrayInputStream(result);
            return stream;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.disconnect();
                is.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }
}
