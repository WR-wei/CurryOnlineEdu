package com.curry.eduservice.service;

import com.curry.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByVideoId(String videoId);

    void removeVideoByCourseId(String courseId);
}
