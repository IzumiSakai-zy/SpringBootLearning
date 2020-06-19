package com.config;

import com.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//标明这是一个配置类
@Configuration
public class MyConfig {

    //把这个方法的返回值添加进spring容器
    @Bean("helloService")
    public HelloService helloService(){
        HelloService service=new HelloService();
        service.setId(50);
        return service;
    }
}
