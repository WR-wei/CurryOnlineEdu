package com.curry.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.curry.eduservice.entity.EduSubject;
import com.curry.eduservice.entity.excel.ObjectData;
import com.curry.eduservice.entity.subject.OneSubject;
import com.curry.eduservice.entity.subject.TwoSubject;
import com.curry.eduservice.listener.SubjectExcelListener;
import com.curry.eduservice.mapper.EduSubjectMapper;
import com.curry.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author curry
 * @since 2021-04-03
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file , EduSubjectService subjectService) {
        try{
            //获取文件输入流
            InputStream in = file.getInputStream();

            //调用方法进行读写
            EasyExcel.read(in, ObjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //获取所有一级分类的课程
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);

        //获取所有二级分类的课程
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        //创建一个list集合,用于存储最终的返回值
        List<OneSubject> finalList = new ArrayList<>();

        //封窗一级分类
        for (EduSubject subject : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject,oneSubject);
            finalList.add(oneSubject);

            List<TwoSubject> listTwo = new ArrayList<>();

            for (EduSubject subject2 : twoSubjects) {
                if (subject.getId().equals(subject2.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject2,twoSubject);
                    listTwo.add(twoSubject);
                }
            }
            oneSubject.setChildren(listTwo);
        }

        return finalList;
    }
}
