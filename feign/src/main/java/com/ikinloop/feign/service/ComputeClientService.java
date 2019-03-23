package com.ikinloop.feign.service;

import com.ikinloop.feign.hystrix.ComputeClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Reace
 * @createTime 23 17:32
 * @description
 */
@FeignClient(value = "compute-service",fallback = ComputeClientHystrix.class)
public interface ComputeClientService {

    @RequestMapping( value = "/add",method = RequestMethod.GET)
    Integer add(@RequestParam(value = "a") Integer a,@RequestParam(value = "b") Integer b);
}
