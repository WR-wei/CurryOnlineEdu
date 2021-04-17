package com.curry.educms.controller;


import com.curry.commonutils.R;
import com.curry.educms.entity.CrmBanner;
import com.curry.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author curry
 * @since 2021-04-11
 * 前台系统接口
 */
@Api(tags = "网站首页Banner列表")
@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

