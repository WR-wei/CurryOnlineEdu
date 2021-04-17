package com.curry.eduservice.controller;


import com.curry.commonutils.R;
import com.curry.eduservice.entity.EduChapter;
import com.curry.eduservice.entity.chapter.ChapterVo;
import com.curry.eduservice.entity.chapter.VideoVo;
import com.curry.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@Api(tags = "章节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("list",list);
    }
    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if(save) return R.ok();
        else return R.error();
    }

    //根据id获取章节
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.updateById(eduChapter);
        if(save) return R.ok();
        else return R.error();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b = eduChapterService.deleteChapter(chapterId);
        return R.ok();
    }
}

