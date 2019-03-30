package com.ikinloop.apigatewayzuul.beanconfig;

import com.ikinloop.apigatewayzuul.utils.JsonAdaptor;
import com.ikinloop.apigatewayzuul.zuulfilter.AccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Reace
 * @createTime 30 13:08
 * @description
 */
@Configuration
public class BeanConfig {

    @Bean
    public JsonAdaptor jsonAdaptor() {
        return new JsonAdaptor();
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
