package com.curry.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.curry.commonutils.JwtUtils;
import com.curry.educenter.entity.vo.RegisterVo;
import com.curry.educenter.utils.MD5;
import com.curry.educenter.entity.UcenterMember;
import com.curry.educenter.mapper.UcenterMemberMapper;
import com.curry.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.curry.servicebase.exceptionhandler.KuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author curry
 * @since 2021-04-12
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String email = member.getEmail();
        String password = member.getPassword();

        //校验参数
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new KuliException(20001,"邮箱或者密码不能为空");
        }

        //获取会员
        UcenterMember mem = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("email", email));

        if(mem == null) {
            throw new KuliException(20001,"用户不存在");
        }

        //校验密码
        if(!MD5.encrypt(password).equals(mem.getPassword())) {
            throw new KuliException(20001,"密码错误");
        }

        //校验是否被禁用
        if(mem.getIsDisabled()) {
            throw new KuliException(20001,"用户被禁用");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(mem.getId(), mem.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String email = registerVo.getEmail();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(email) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new KuliException(20001,"注册失败");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String emailCode = (String)redisTemplate.opsForValue().get(email);
        if(!code.equals(emailCode)) {
            throw new KuliException(20001,"验证码不正确");
        }

        //查询数据库中是否存在相同的邮箱
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("email", email));
        if(count.intValue() > 0) {
            throw new KuliException(20001,"手机已被注册");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setEmail(registerVo.getEmail());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);

        //默认头像
        member.setAvatar("https://curry-edu.oss-cn-shanghai.aliyuncs.com/nezha.jpg");
        this.save(member);

    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
