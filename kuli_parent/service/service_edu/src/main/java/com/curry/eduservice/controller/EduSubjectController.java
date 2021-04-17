package com.curry.eduservice.controller;


import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduSubject;
import com.curry.eduservice.entity.EduTeacher;
import com.curry.eduservice.entity.subject.OneSubject;
import com.curry.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-04-03
 */
@Api(tags = "课程分类管理")
@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    //添加课程分类
    @PostMapping("saveSubject")
    public R saveSubject(MultipartFile file){


        subjectService.saveSubject(file,subjectService);

        return R.ok();
    }

    //课程分类列表
    @GetMapping("getAllObject")
    public R getAllObject(){
        //获取所有的一级分类class
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }


}

