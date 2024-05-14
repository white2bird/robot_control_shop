package com.it.service.impl;

import com.it.constants.RequestPathContext;
import com.it.constants.VideoType;
import com.it.entity.Resources;
import com.it.service.ManagerService;
import com.it.service.ResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @date 2024/4/20 15:06
 */
@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private RequestPathContext requestPathContext;

    @Resource
    private ResourcesService resourcesService;

    @Resource
    private Tika tika;

    @Override
    public String saveFile(MultipartFile file) {
        if(!file.getOriginalFilename().contains(".")){
            throw new RuntimeException("非法的文件");
        }
        String typename = null;
        try {
            typename = tika.detect(file.getInputStream());
        }catch (Exception e){
            throw new RuntimeException("错误的文件类型");
        }
        if(StringUtils.isEmpty(typename)){
            return null;
        }

        // 后期进行抽象 md 时间不够了
        Integer fileType = getFileType(typename);
        // 如果文件类型是1的话 证明是视频需要自己去生成第一帧
        String nameWithoutSuf = file.getOriginalFilename().split("\\.")[0];
        String post = file.getOriginalFilename();

        try {
            // 本地文件保存位置
            String uploadPath = "/Users/mico/uploadFile"; // 改这里
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            log.info("upload path  {} ", uploadDir.getAbsolutePath());
            // 本地文件 存在则不进行处理
            File localFile = new File(uploadPath + File.separator + file.getOriginalFilename());
            if(localFile.exists()){
                return requestPathContext.getPath() + post;
            }
            // transfer to local
            file.transferTo(localFile);
            if(VideoType.VIDEO == fileType){
                post = nameWithoutSuf + ".jpg";
                captureFirstFrame(uploadPath + File.separator + file.getOriginalFilename(), uploadPath + File.separator + nameWithoutSuf + ".jpg");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Resources resources = new Resources();
        resources.setHref(file.getOriginalFilename());
        resources.setTitle(nameWithoutSuf);
        resources.setTypeInt(fileType);
        resources.setType(typename);
        resources.setThumbnail(post);
        resourcesService.save(resources);
        return requestPathContext.getPath() + post;
    }

    public void captureFirstFrame(String videoPath, String outputPath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);

        try {
            grabber.start();

            // 读取第一帧
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage firstFrame = converter.convert(grabber.grabImage());

            // 将图像保存到指定路径
            ImageIO.write(firstFrame, "jpg", new File(outputPath));

            grabber.stop();
        } catch (IOException e) {
            throw new RuntimeException("无法截取第一帧图像");
        }
    }

    public Integer getFileType(String typename){
        if(typename.contains("video")){
            return VideoType.VIDEO;
        }
        if(typename.contains("image")){
            return VideoType.PIC;
        }
        return VideoType.VOICE;
    }


}
