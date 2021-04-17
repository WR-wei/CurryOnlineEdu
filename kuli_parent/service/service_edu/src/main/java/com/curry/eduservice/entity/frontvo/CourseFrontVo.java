package com.curry.eduservice.entity.frontvo;


import lombok.Data;

/**
 * @author curry30
 * @create 2021-04-13 18:00
 */
@Data
public class CourseFrontVo {

    //课程名称
    private String title;

    //讲师id
    private String teacherId;

    //一级类别id
    private String subjectParentId;

    //二级类别id
    private String subjectId;

    //销量排序
    private String buyCountSort;

    //最新时间排序
    private String gmtCreateSort;

    //价格排序
    private String priceSort;
}
