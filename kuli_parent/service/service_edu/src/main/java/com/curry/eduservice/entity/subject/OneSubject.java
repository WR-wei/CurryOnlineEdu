package com.curry.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author curry30
 * @create 2021-04-03 15:23
 */
@Data
public class OneSubject {
    private String id;

    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
