package com.it.util;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
public class FileDetectUtils {

    /**
     * 文件大小
     */
    private static final long MAX_FILE_SIZE = 10485760L;
    private static final List<String> MEDIA_TYPE_LIST = Lists.newArrayList("image","video");
    private static final Detector DETECTOR = TikaConfig.getDefaultConfig().getDetector();

    /**
     * 文件检测
     * @param file
     * @return
     */
    public static String detect(MultipartFile file) {
        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("上传文件过大,请上传低于" + MAX_FILE_SIZE + "文件");
        }
        // 检查文件名 以.开头的文件不支持上传
        isValidFileName(file.getOriginalFilename());
        String extension = getFileExtension(file.getOriginalFilename());

        // 使用Tika检测文件类型
        MediaType mediaType;
        List<String> mediaTypeExtensions;
        try (TikaInputStream tis = TikaInputStream.get(file.getBytes())) {
            mediaType = DETECTOR.detect(tis, new Metadata());
            if (!MEDIA_TYPE_LIST.contains(mediaType.getType())) {
                log.error("不支持的文件类型,type:{}", mediaType.getType());
                throw new RuntimeException("不支持的文件类型");
            }
            mediaTypeExtensions = MimeTypes.getDefaultMimeTypes().forName(mediaType.toString()).getExtensions();
        } catch (IOException e) {
            log.error("Could not read file", e);
            throw new RuntimeException("请上传正确的文件类型");
        } catch (MimeTypeException e) {
            log.error("Could not get extension for media type", e);
            throw new RuntimeException("请上传正确的文件类型");
        }
        // 验证文件类型
        if (!mediaTypeExtensions.contains(extension.toLowerCase())) {
            throw new RuntimeException("文件类型已被修改，不支持上传");
        }
        return mediaType.getType();

        // 如果需要，你可以在这里添加更深入的文件内容检查
        // 例如，对于PDF文件，你可能希望检查是否包含JavaScript或宏
    }

    /**
     * 校验文件名
     * @param fileName
     */
    private static void isValidFileName(String fileName) {
        if (null == fileName) {
            throw new RuntimeException("文件名不能为空");
        }
        // 简单的文件名检查，需要根据需求进行修改
        if (fileName.startsWith(".")) {
            throw new RuntimeException("不支持以.开头的文件");
        }
    }

    /**
     * 因为tika解析出的文件后缀带了.   所以此处不去除
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new RuntimeException("文件名无扩展名，无法确定文件类型");
        }
        // 包含点，所以使用dotIndex
        return fileName.substring(dotIndex);
    }
}
