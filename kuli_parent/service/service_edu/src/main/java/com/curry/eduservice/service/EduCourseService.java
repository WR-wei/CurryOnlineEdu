package com.curry.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.curry.eduservice.entity.frontvo.CourseFrontVo;
import com.curry.eduservice.entity.frontvo.CourseWebVo;
import com.curry.eduservice.entity.vo.CourseInfoVo;
import com.curry.eduservice.entity.vo.CoursePublishVo;
import com.curry.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfo);

    CoursePublishVo getCoursePublishVo(String courseId);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourse(String courseId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
