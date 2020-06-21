package com.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class EmployeeControler {

    @RequestMapping(value = {"/emps"},method = RequestMethod.GET)
    public String list(){
        return "list";
    }
}
