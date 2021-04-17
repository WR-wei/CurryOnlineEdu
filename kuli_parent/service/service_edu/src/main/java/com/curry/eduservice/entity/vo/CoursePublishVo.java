package com.curry.eduservice.entity.vo;

import lombok.Data;

/**
 * @author curry30
 * @create 2021-04-07 15:10
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
