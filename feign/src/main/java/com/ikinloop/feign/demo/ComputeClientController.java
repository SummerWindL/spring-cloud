package com.ikinloop.feign.demo;

import com.ikinloop.feign.service.ComputeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reace
 * @createTime 23 17:28
 * @description
 */
@RestController
public class ComputeClientController {

    @Autowired
    ComputeClientService computeClientService;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public Integer add(){
        return computeClientService.add(10,20);
    }
}
