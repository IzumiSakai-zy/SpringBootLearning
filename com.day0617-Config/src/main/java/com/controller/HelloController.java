package com.controller;

import com.bean.Person;
import com.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {
    @Autowired
    Person person;
    @Resource(name = "helloService")
    HelloService service;

    @RequestMapping("/test")
    public String sayHello(){
        return "hello world second time";
    }

    @RequestMapping("/person")
    public String person(){
        return person.toString();
    }

    @RequestMapping("/service")
    public Integer service(){
        return service.getId();
    }
}
