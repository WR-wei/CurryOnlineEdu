package com.curry.eduservice.controller;


import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduVideo;
import com.curry.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@Api(tags = "小节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        boolean save = videoService.save(video);
        if (save) return R.ok();
        else return R.error();
    }

    //删除小节
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        videoService.removeVideoByVideoId(videoId);
        return R.ok();
    }

    //通过id查询小节
    @GetMapping("{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        boolean save = videoService.updateById(video);
        if (save) return R.ok();
        else return R.error();
    }

}

