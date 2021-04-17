package com.curry.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author curry30
 * @create 2021-04-01 11:47
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
