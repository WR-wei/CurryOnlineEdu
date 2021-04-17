package com.curry.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.curry.commonutils.R;
import com.curry.eduservice.client.VodClient;
import com.curry.eduservice.entity.EduVideo;
import com.curry.eduservice.mapper.EduVideoMapper;
import com.curry.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.curry.servicebase.exceptionhandler.KuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author curry
 * @since 2021-04-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    @Override
    public void removeVideoByVideoId(String videoId) {
        EduVideo eduVideo = this.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (StringUtils.hasLength(videoSourceId)){
            R result = vodClient.removeAliyunVideo(videoSourceId);
            if (result.getCode() == 20001){
                throw new KuliException(20001,"删除视频失败,熔断器...");
            }
        }
        boolean b = this.removeById(videoId);
        if (!b) new KuliException(20001,"删除小节异常");

    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询所有视频列表
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);

        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.deleteBatch(videoSourceIdList);
        }

    }
}
