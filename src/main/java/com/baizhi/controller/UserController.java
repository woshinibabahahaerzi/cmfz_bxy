package com.baizhi.controller;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping("selectAll")
    public String selectAll(HttpSession session){
        List<User> users = userService.selectAll();
        session.setAttribute("users",users);
        return "index";
    }

    @RequestMapping("selectCount")
    @ResponseBody
    public Map<String,Object> selectCount(){
        int count = userDAO.selectCount(new User());
        Map<String, Object> map = new HashMap<>();
        map.put("nan1",count);
        map.put("nan2",count+5);
        map.put("nan3",count+13);
        map.put("nv1",count+64);
        map.put("nv2",count+88);
        map.put("nv3",count+1);
        return map;
    }


    @RequestMapping("addUser")
    @ResponseBody
    public void addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        int i = userDAO.insertSelective(user);
        if (i==1){
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-e5d4279b789f4f21903f02cf71db6e00");
            goEasy.publish("myTest", "添加"+i+"个用户成功");
        }
    }


}
