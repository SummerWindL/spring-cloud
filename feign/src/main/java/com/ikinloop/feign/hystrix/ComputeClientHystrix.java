package com.ikinloop.feign.hystrix;

import com.ikinloop.feign.service.ComputeClientService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Reace
 * @createTime 23 18:51
 * @description
 */
@Component
public class ComputeClientHystrix implements ComputeClientService {


    @Override
    public Integer add(@RequestParam(value = "a")Integer a,@RequestParam(value = "b") Integer b) {
        return -9999;
    }
}
