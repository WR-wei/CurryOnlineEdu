package com.curry.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.curry.eduservice.entity.EduChapter;
import com.curry.eduservice.entity.EduVideo;
import com.curry.eduservice.entity.chapter.ChapterVo;
import com.curry.eduservice.entity.chapter.VideoVo;
import com.curry.eduservice.mapper.EduChapterMapper;
import com.curry.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.curry.eduservice.service.EduVideoService;
import com.curry.servicebase.exceptionhandler.KuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> listChapter = this.list(wrapperChapter);

        //所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> listVideo = videoService.list(wrapperVideo);

        //创建list集合用于返回数据
        List<ChapterVo> list = new ArrayList<>();

        //封装所有章节和小节
        for (EduChapter chapter : listChapter) {

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            list.add(chapterVo);

            List<VideoVo> li = new ArrayList<>();
            for (EduVideo video : listVideo) {
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    li.add(videoVo);
                }
            }
            chapterVo.setChildren(li);
        }

        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count > 0){
            throw new KuliException(20001,"还存在小节，不能删除");
        }else{
            return this.removeById(chapterId);
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        boolean remove = this.remove(wrapper);
        if (!remove) new KuliException(20001,"删除小节异常");

    }
}
