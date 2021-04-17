package com.curry.eduservice.service;

import com.curry.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.curry.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
