package com.curry.educenter.entity.vo;

import lombok.Data;

/**
 * @author curry30
 * @create 2021-04-12 20:12
 */

@Data
public class RegisterVo {

    private String nickname;

    private String email;

    private String password;

    private String code;//邮箱验证码
}
