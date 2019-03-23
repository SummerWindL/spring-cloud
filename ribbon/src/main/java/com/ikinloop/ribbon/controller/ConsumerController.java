package com.ikinloop.ribbon.controller;

import com.ikinloop.ribbon.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Reace
 * @createTime 23 15:40
 * @description
 */
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    @ResponseBody
    public String add(){

        return consumerService.addService();
        /*return restTemplate.getForEntity("http://compute-service/add?a=10&b=20",String.class).getBody();*/

    }
}
