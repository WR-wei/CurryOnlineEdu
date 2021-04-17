package com.curry.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author curry30
 * @create 2021-04-08 9:14
 * 用于条件查询课程
 */
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

}
