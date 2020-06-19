package com.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("/success")
    public String success(Map<String,String> map){
        map.put("name","IzumiSakai");
        return "success";
    }
}
