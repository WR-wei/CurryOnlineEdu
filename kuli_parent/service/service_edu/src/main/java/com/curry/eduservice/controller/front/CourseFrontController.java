package com.curry.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curry.commonutils.JwtUtils;
import com.curry.commonutils.R;
import com.curry.commonutils.ordervo.CourseWebVoOrder;
import com.curry.eduservice.client.OrdersClient;
import com.curry.eduservice.entity.EduCourse;
import com.curry.eduservice.entity.chapter.ChapterVo;
import com.curry.eduservice.entity.frontvo.CourseFrontVo;
import com.curry.eduservice.entity.frontvo.CourseWebVo;
import com.curry.eduservice.service.EduChapterService;
import com.curry.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author curry30
 * @create 2021-04-13 15:24
 */
@Api(tags = "前台课程管理")
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private OrdersClient ordersClient;

    //分页课程列表
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
                      @PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseFrontVo);
        return  R.ok().data(map);
    }
    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询当前课程是否已经支付过了
        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);

    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
