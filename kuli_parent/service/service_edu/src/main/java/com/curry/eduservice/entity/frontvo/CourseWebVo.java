package com.curry.eduservice.entity.frontvo;

/**
 * @author curry30
 * @create 2021-04-14 15:59
 * 网站课程详情页需要的相关字段
 */

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseWebVo{

    private String id;

    private String title;

    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private Long buyCount;

    private Long viewCount;

    private String description;

    private String teacherId;

    private String teacherName;

    private String intro;

    private String avatar;

    private String subjectLevelOneId;

    private String subjectLevelOne;

    private String subjectLevelTwoId;

    private String subjectLevelTwo;

}
