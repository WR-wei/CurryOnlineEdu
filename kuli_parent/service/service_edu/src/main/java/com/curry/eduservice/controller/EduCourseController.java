package com.curry.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduCourse;
import com.curry.eduservice.entity.vo.CourseInfoVo;
import com.curry.eduservice.entity.vo.CoursePublishVo;
import com.curry.eduservice.entity.vo.CourseQuery;
import com.curry.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@CrossOrigin
@RestController
@Api(tags = "课程管理")
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //查询所有课程
    @GetMapping("getAllCourses")
    public R getAllCourses() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);
        return R.ok().data("courseId", id);
    }

    //根据课程查询课程id
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("course", courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    //根据课程id获取课程最终确认信息
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(courseId);
        return R.ok().data("coursePublish", coursePublishVo);
    }

    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");//设置课程发布状态
        courseService.updateById(course);
        return R.ok();
    }

    @GetMapping("coursePage/{page}/{limit}")
    public R pageQuery(@PathVariable Long page,
                       @PathVariable Long limit,
                       CourseQuery courseQuery) {

        Page<EduCourse> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);

    }
    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}