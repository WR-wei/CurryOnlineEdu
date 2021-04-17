package com.curry.edumsm.controller;

import com.curry.commonutils.R;
import com.curry.edumsm.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static com.curry.edumsm.utils.VerCodeGenerateUtil.generateVerCode;

/**
 * @author curry30
 * @create 2021-04-12 15:33
 */
@Api(tags = "短信验证")
@CrossOrigin
@RestController
@RequestMapping("/edumsm/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("send/{emailNumId}")
    public R sendEmail(@PathVariable String emailNumId) {

        //从redis获取验证码，如果获取到直接返回
        String code = (String) redisTemplate.opsForValue().get(emailNumId);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }

        try {
            code = generateVerCode();
            emailService.sendEmailVerCode(emailNumId,code);
            redisTemplate.opsForValue().set(emailNumId,code,5, TimeUnit.MINUTES);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }
}
