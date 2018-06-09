package com.hgo.book.controller;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.hgo.book.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    Gson gson = new Gson();

    @RequestMapping(value = "login")
    @ResponseBody
    public String login(
            HttpServletRequest request,
            @RequestParam(value = "mobile") String mobile,
            @RequestParam(value = "password") String password
            ) throws IOException {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String token = "";
        try {
            token = userService.checkLogin(mobile,password);
        } catch (Exception e) {
            map.put("status","error");
            map.put("reason",e.getMessage());
            return gson.toJson(map);
        }
        map.put("token",token);
        map.put("status","success");
        return gson.toJson(map);
    }

}
