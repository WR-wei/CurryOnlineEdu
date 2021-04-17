package com.curry.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduCourse;
import com.curry.eduservice.entity.EduTeacher;
import com.curry.eduservice.service.EduCourseService;
import com.curry.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author curry30
 * @create 2021-04-13 15:24
 */
@Api(tags = "前台讲师管理")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @PostMapping(value = "{page}/{limit}")
    public R pageList( @PathVariable Long page,@PathVariable Long limit){

        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

        Map<String, Object> map = teacherService.pageListWeb(pageParam);

        return  R.ok().data(map);
    }

    //根据讲师id查询讲师详细信息
    @GetMapping(value = "getTeacherFrontInfo/{id}")
    public R getById(@PathVariable String id){

        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
