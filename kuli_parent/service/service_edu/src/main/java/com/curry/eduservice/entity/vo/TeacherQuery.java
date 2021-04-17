package com.curry.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import javafx.scene.chart.ValueAxis;
import lombok.Data;

/**
 * @author curry30
 * @create 2021-03-28 15:29
 */
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师名称")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 10:10:10")
    private String begin;//利用String保存时间,前端传过来的数据不需要进行类型转换

    @ApiModelProperty(value = "查询结束时间",example = "2019-01-01 10:10:10")
    private String end;
}
