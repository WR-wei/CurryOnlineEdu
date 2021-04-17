package com.curry.eduservice.client;

import com.curry.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author curry30
 * @create 2021-04-11 8:55
 * 熔断器，出错之后会执行这些方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliyunVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
