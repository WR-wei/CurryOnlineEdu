package com.curry.eduservice.controller;

import com.curry.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author curry30
 * @create 2021-03-31 9:42
 */
@Api(tags = "注册登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R getInfo(){
        return R.ok().data("roles","[admin]")
                     .data("name","admin")
                     .data("avatar","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2471443524,3225356004&fm=26&gp=0.jpg");
    }

}
