package com.curry.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.curry.commonutils.R;
import com.curry.servicebase.exceptionhandler.KuliException;
import com.curry.vod.service.VodService;
import com.curry.vod.utils.AliyunVodSDKUtils;
import com.curry.vod.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author curry30
 * @create 2021-04-01 11:46
 */
@Api(tags = "阿里云视频点播微服务")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public R uploadVideo(@RequestParam("file") MultipartFile file) throws Exception {

        String videoId = vodService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId){
        vodService.removeVideo(videoId);
        return R.ok();
    }

    //删除多个阿里云视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreVideo(videoIdList);
        return R.ok();
    }

    //获取视频凭证，根据视频id
    @GetMapping("getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId){

        try{
            //获取阿里云存储相关常量
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return R.ok().data("playAuth", playAuth);
        }catch(Exception e){
            throw new KuliException(20001,"获取视频凭证失败");
        }

    }
}
