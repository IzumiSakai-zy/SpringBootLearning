package com.demo.controller;

import com.demo.entity.Account;
import com.demo.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {
    @Autowired
    AccountDao accountDao;

    @ResponseBody
    @RequestMapping("/findAll")
    public String findAll(){
        return accountDao.findAll().toString();
    }

    @ResponseBody
    @RequestMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id){
        return accountDao.findById(id).toString();
    }

    @ResponseBody
    @RequestMapping("/add")
    public String add(){
        Account account=new Account();
        account.setUserId(2);
        account.setMoney(2000.0);
        accountDao.add(account);
        return "添加成功";
    }

    @ResponseBody
    @RequestMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        accountDao.deleteById(id);
        return "删除成功";
    }

    @ResponseBody
    @RequestMapping("/update")
    public String update(){
        Account account=new Account();
        account.setId(1);
        account.setUserId(3);
        account.setMoney(3988.1);
        accountDao.update(account);
        return "更新成功";
    }
}
