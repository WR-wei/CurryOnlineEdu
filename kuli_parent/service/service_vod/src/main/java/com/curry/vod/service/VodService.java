package com.curry.vod.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author curry30
 * @create 2021-04-01 11:47
 */
public interface VodService {

    String uploadVideo(MultipartFile file);

    void removeVideo(String videoId);

    void removeMoreVideo(List videoIdList);
}
