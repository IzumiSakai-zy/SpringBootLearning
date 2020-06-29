package com.example.demo.controller;

import com.example.demo.entities.Account;
import com.example.demo.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class AccountController {
    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping(value = "/accounts",method = RequestMethod.GET)
    public String list(Map<String,Object> map){
        List<Account> accounts = accountMapper.findAll();
        System.out.println(accounts.get(0));
        map.put("accounts",accounts);
        return "accountList";
    }
}
