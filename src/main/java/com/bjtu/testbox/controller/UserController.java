package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /*@RequestMapping("getUser/{id}")
    public User GetUser(@PathVariable int id) {
        return userService.Sel(id);
    }*/
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public User GetUser(HttpServletRequest request,
                        @RequestParam(value = "id", required = true, defaultValue = "1") int id) {
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
}