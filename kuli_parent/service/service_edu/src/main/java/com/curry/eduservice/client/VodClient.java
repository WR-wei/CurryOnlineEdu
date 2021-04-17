package com.curry.eduservice.client;

import com.curry.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author curry30
 * @create 2021-04-10 10:43
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用方法路径
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable("videoId") String videoId);

    //删除多个阿里云视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
