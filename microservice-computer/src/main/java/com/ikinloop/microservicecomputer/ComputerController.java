package com.ikinloop.microservicecomputer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reace
 * @createTime 23 15:10
 * @description
 */

@RestController
public class ComputerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    /**
     * 计算a + b 的微服务
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Integer add(@RequestParam Integer a,@RequestParam Integer b){

        ServiceInstance instance = client.getLocalServiceInstance();

        Integer r = a + b;

        logger.info("/add ,host:" + instance.getHost() + ",service_id:"+instance.getServiceId() + ",result:" + r);
        return r;
    }

}
