package com.curry.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.curry.eduservice.entity.EduSubject;
import com.curry.eduservice.entity.excel.ObjectData;
import com.curry.eduservice.service.EduSubjectService;
import com.curry.servicebase.exceptionhandler.KuliException;

import java.util.Map;

/**
 * @author curry30
 * @create 2021-04-03 11:58
 */
public class SubjectExcelListener extends AnalysisEventListener<ObjectData> {

    //这个类不能交给spring进行管理
    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ObjectData data, AnalysisContext context) {
        if (data == null){
            throw new KuliException(20001,"文件内容为空");
        }

        //判断一级分类是否重复
        EduSubject oneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if (oneSubject == null){//没有一级分类
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(data.getOneSubjectName());
            subjectService.save(oneSubject);
        }

        //获取一级分类的id值
        String pid = oneSubject.getId();

        //判断二级分类是否重复
        EduSubject twoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(), pid);
        if (twoSubject == null){//没有二级分类
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(data.getTwoSubjectName());
            subjectService.save(twoSubject);
        }

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
