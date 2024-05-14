package com.it.util;

import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author 
 * @date 2024/4/8 15:45
 */
public class DownLoadUtils {

    public static void download(String filename, HttpServletResponse res, String data) throws IOException {
        // 发送给客户端的数据
        // 读取filename
        byte[] bytes = data.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        InputStream inputStream =byteArrayInputStream;
        bis = new BufferedInputStream(inputStream);
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, i);
            outputStream.flush();
            i = bis.read(buff);
        }
        bis.close();
        outputStream.close();
    }

}
