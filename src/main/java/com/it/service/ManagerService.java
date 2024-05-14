package com.it.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @date 2024/4/20 15:06
 */

public interface ManagerService {

    String saveFile(MultipartFile file);
}
