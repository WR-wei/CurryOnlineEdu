package com.curry.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.eduservice.entity.EduCourse;
import com.curry.eduservice.entity.EduCourseDescription;
import com.curry.eduservice.entity.frontvo.CourseFrontVo;
import com.curry.eduservice.entity.frontvo.CourseWebVo;
import com.curry.eduservice.entity.vo.CourseInfoVo;
import com.curry.eduservice.entity.vo.CoursePublishVo;
import com.curry.eduservice.entity.vo.CourseQuery;
import com.curry.eduservice.mapper.EduCourseMapper;
import com.curry.eduservice.service.EduChapterService;
import com.curry.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.curry.eduservice.service.EduVideoService;
import com.curry.servicebase.exceptionhandler.KuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionServiceImpl courseDescService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;


    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfo) {
        //向课程表中添加课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo,course);
        boolean save = this.save(course);
        if (!save){
            throw new KuliException(20001,"添加课程信息失败");
        }
        String id = course.getId();
        //向课程简介表中添加课程简介
        EduCourseDescription courseDesc = new EduCourseDescription();
        courseDesc.setId(id);
        courseDesc.setDescription(courseInfo.getDescription());
        boolean descSave = courseDescService.save(courseDesc);

        if (!descSave){
            throw new KuliException(20001,"添加课程描述信息失败");
        }

        return id;
    }
    //通过课程id获取课程信息
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //查询课程表
        EduCourse eduCourse = this.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述表
        EduCourseDescription courseDescription = courseDescService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }
    //修改课程
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfo) {
        //修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo,course);
        boolean b = this.updateById(course);
        if (!b){
            throw new KuliException(20001,"修改课程表失败");
        }

        //修改描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());

    }

    @Override
    public CoursePublishVo getCoursePublishVo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (StringUtils.hasLength(title)) {
            queryWrapper.like("title", title);
        }

        if (StringUtils.hasLength(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (StringUtils.hasLength(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (StringUtils.hasLength(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3 根据课程id删除课程描述
        boolean b1 = courseDescService.removeById(courseId);
        //4 根据课程id删除课程
        boolean b2 = this.removeById(courseId);

        if (!b1 || !b2) throw new KuliException(20001,"删除失败");
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
