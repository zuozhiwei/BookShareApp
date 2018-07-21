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
     * @param borrowStatus 书的状态（0：在借中，1：空闲图书）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBookList",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String getBookList(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookName",required = false) String bookName,
            @RequestParam(value = "borrowStatus",required = false) String borrowStatus
    ) throws Exception {
        List<HashMap<String,Object>> list = null;
        try {
            if (null == bookName) {
                bookName = "";
            }
            userService.checkToken(token);
            list = userService.getBookList(bookName,borrowStatus,null);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,list);
    }

    /**
     * 添加图书
     * @param token     token
     * @param bookName  图名
     * @param bookAuthor    作者
     * @param bookDesc      图书简介
     * @param bookPrice     图书价格
     * @param bookLabel     图书标签
     * @param bookMessage   主人推荐
     * @param bookCoverUrl  封面url
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadBook",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String uploadBook(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "bookAuthor") String bookAuthor,
            @RequestParam(value = "bookDesc") String bookDesc,
            @RequestParam(value = "bookPrice") String bookPrice,
            @RequestParam(value = "bookLabel",required = false) String bookLabel,
            @RequestParam(value = "bookMessage",required = false) String bookMessage,
            @RequestParam(value = "bookCoverUrl",required = false) String bookCoverUrl
    ) throws Exception {
        try {
            userService.checkToken(token);
            userService.uploadBook(token,bookName,bookAuthor,bookDesc,bookPrice,bookLabel,bookMessage,bookCoverUrl);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,null);
    }

    /**
     * 借书
     * @param token     token
     * @param bookID    图书id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowBook",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String borrowBook(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookID") String bookID
    ) throws Exception {
        try {
            userService.checkToken(token);
            userService.borrowBook(token,bookID);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,null);
    }

    /**
     * 还书
     * @param token     token
     * @param bookID    图书id（可选）
     * @param recordID  借书记录id（可选）二选一
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "returnBook",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String returnBook(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookID", required = false) String bookID,
            @RequestParam(value = "recordID", required = false) String recordID
    ) throws Exception {
        try {
            userService.checkToken(token);
            userService.returnBook(token,bookID,recordID);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,null);
    }

    /**
     * 查看我的图书
     * @param token         token
     * @param bookName      书名（可选，模糊查询）
     * @param bookStatus    图书状态 可选（0：在借中，1：空闲图书）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "myBook",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String myBook(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookName",required = false) String bookName,
            @RequestParam(value = "bookStatus",required = false) String bookStatus
            ) throws Exception {
        List<HashMap<String,Object>> list = null;
        try {
            userService.checkToken(token);
            list = userService.getBookList(bookName,bookStatus,token);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,list);
    }
}