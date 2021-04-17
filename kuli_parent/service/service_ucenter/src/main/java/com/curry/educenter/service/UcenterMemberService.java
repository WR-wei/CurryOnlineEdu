package com.curry.educenter.service;

import com.curry.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.curry.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author curry
 * @since 2021-04-12
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录方法
    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    Integer countRegisterDay(String day);
}
