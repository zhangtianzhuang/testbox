package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getApprover/{id}")
    public Approver GetUser(@PathVariable int id) {
        return userService.select(id);
    }

    @RequestMapping("getWorker/{id}")
    public Worker GetWorker(@PathVariable int id) {
        return userService.selectWorker(id);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public User GetUser(HttpServletRequest request,
                        @RequestParam(value = "id", required = true,
                                defaultValue = "1") int id) {
        return userService.Sel(id);
    }

    @RequestMapping("get/{username}")
    public User findUserbyName(@PathVariable String username){
        return userService.selectByUsername(username);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTag(HttpServletRequest request,
                          @RequestParam(value = "name", required = true) String name,
                          @RequestParam(value = "level", required = true) Integer level) {
        try {
            return "recive your param " + "name: " + name + " level: " + level;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/test001")
    public List<User> test001(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User u = new User();
            u.setId(i);
            u.setPassWord("pass"+i);
            u.setRealName("real"+i);
            u.setUserName("name"+i);
            users.add(u);
        }
        return users;
    }
}