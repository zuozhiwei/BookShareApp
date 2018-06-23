package com.hgo.book.controller;

import javax.servlet.http.HttpServletRequest;

import com.hgo.book.service.IUserService;
import com.hgo.book.tool.JsonTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;


    /**
     * 用户登录
     * @param request
     * @param mobile 手机号码
     * @param password 密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "login",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String login(
            HttpServletRequest request,
            @RequestParam(value = "mobile") String mobile,
            @RequestParam(value = "password") String password
            ) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String token = "";
        try {
            // 先检查用户名和密码是否匹配
            // 再更新token
            userService.checkLogin(mobile,password);
            token = userService.updateToken(mobile);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        HashMap<String,Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("token",token);
        return JsonTool.returnPackage("success",null,tokenMap);
    }

    /**
     * 用户注册
     * @param request
     * @param mobile 手机号码
     * @param password 密码
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "register",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String register(
            HttpServletRequest request,
            @RequestParam(value = "mobile") String mobile,
            @RequestParam(value = "password") String password
    ) throws Exception{
        try {
            userService.register(mobile,password);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,null);
    }

    /**
     * 获取图书列表
     * @param token token
     * @param bookName 书名（不传查全部，传值模糊查询）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBookList",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String getBookList(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookName",required = false) String bookName
    ) throws Exception {
        List<HashMap<String,Object>> list = null;
        try {
            if (null == bookName) {
                bookName = "";
            }
            userService.checkToken(token);
            list = userService.getBookList(bookName);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,list);
    }
}