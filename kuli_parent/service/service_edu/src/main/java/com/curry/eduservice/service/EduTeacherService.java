package com.curry.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author curry
 * @since 2021-03-28
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
