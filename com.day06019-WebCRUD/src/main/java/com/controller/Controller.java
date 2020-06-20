package com.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("/success")
    public String success(Map<String,String> map){
        map.put("name","IzumiSakai");
        return "success";
    }

    @RequestMapping("/array")
    public String array(Map<String,Object> map){
        map.put("array", Arrays.asList("IzumiSakai","ZY","Name"));
        return "array";
    }

    @RequestMapping("/dashboard.html")
    public String dashborad(){
        return "dashboard";
    }

    @RequestMapping({"/","/index.html"})
    public String login(){
        return "login";
    }
}
