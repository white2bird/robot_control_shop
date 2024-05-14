package com.it.controller;

import com.it.common.Result;
import com.it.service.ManagerService;
import com.it.util.FileDetectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 
 * @date 2024/4/17 18:47
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private Tika tika;

    @Resource
    private ManagerService managerService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file) throws IOException {
        String type = tika.detect(file.getInputStream());
        log.info("file type is {}", type);
        String result = managerService.saveFile(file);
        return Result.ok(result, 200, "success");
    }
}
