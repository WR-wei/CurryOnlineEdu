package com.curry.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduTeacher;
import com.curry.eduservice.entity.vo.TeacherQuery;
import com.curry.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-03-28
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //rest风格(查询get，删除delete，修改put，添加post)
    //查询表中的所有数据
    @ApiOperation(value = "查询所有")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service中的数据
        List<EduTeacher> list = teacherService.list(null);

//        try {
//            int i = 10 / 0;//异常处理测试
//        } catch (Exception e) {
//            throw new KuliException(20001,"执行了自定义异常处理...");
//        }

        return R.ok().data("items", list);
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R removeTeacherById(@PathVariable String id) {

        boolean flag = teacherService.removeById(id);

        return flag == true ? R.ok() : R.error();
    }

    //分页查询讲师数据(current表示当前页，limit表示每页记录数)
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current,
                         @PathVariable long limit) {

        Page<EduTeacher> page = new Page(current, limit);

        IPage<EduTeacher> page1 = teacherService.page(page, null);

        long total = page1.getTotal();//总记录数
        List<EduTeacher> records = page1.getRecords();//当前页数据

        Map<String, Object> map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }

    //分页条件组合查询讲师数据(current表示当前页，limit表示每页记录数)
    @ApiOperation(value = "分页条件组合查询")
    @PostMapping("pageTeacherConditions/{current}/{limit}")
    public R pageTeacherConditions(@PathVariable long current, @PathVariable long limit,
                                   @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> page = new Page(current, limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (StringUtils.hasLength(name)) {
            wrapper.like("name", name);
        }

        if (level != null) {
            wrapper.eq("level", level);
        }

        if (StringUtils.hasLength(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (StringUtils.hasLength(end)) {
            wrapper.ge("gmt_modified", end);
        }

        wrapper.orderByDesc("gmt_create");

        IPage<EduTeacher> page1 = teacherService.page(page, wrapper);

        long total = page1.getTotal();//总记录数
        List<EduTeacher> records = page1.getRecords();//当前页数据

        Map<String, Object> map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }

    //添加讲师的操作
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id查询讲师
    @ApiOperation(value = "根据id查询")
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {

        EduTeacher teacher = teacherService.getById(id);

        return R.ok().data("item", teacher);
    }

    //根据id修改讲师
    @ApiOperation(value = "根据id修改")
    @PostMapping("update")
    public R updateById(@RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.updateById(eduTeacher);

        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

