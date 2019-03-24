package com.ikinloop.centercomfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reace
 * @createTime 24 13:24
 * @description
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @Autowired
    private Environment environment;
    @RequestMapping("/get_name")
    public String name(){
        return "from:"+from;
    }
    @RequestMapping("/get_name_env")
    public String name_env(){
        return environment.getProperty("from","undefine");
    }
}