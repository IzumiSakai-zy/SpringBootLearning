package com.example.demo.controller;

import com.example.demo.entities.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String listPage(Map<String,Object> map){
        List<User> users = userMapper.findAll();
        map.put("users",users);
        return "userList";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String userAddPage(){
        System.out.println("GET方法执行");
        return "userAdd";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String userAdd(User user){
        userMapper.insert(user);
        System.out.println("POST方法执行");
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String userEditPage(Map<String,Object> map,@PathVariable("id") Integer id){
        User user = userMapper.findById(id);
        map.put("user",user);
        return "userEdit";
    }

    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String userEdit(User user){
        userMapper.update(user);
        System.out.println("PUT方法执行");
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public String userDelete(@PathVariable("id") Integer id){
        userMapper.delete(id);
        System.out.println("DELETE方法执行");
        return "redirect:/users";
    }
}
