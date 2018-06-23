package com.hgo.book.controller;

import com.hgo.book.service.IBookService;
import com.hgo.book.service.IUserService;
import com.hgo.book.tool.JsonTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/book")
public class BookController {

    @Resource
    private IBookService bookService;
    private IUserService userService;

    /**
     * 根据图书id获取图书详情
     * @param token
     * @param bookID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBookInfoByBookID",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String getBookInfoByBookID(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "bookID") String bookID
    ) throws Exception{
        HashMap<String,Object> bookInfo = null;
        try {
            userService.checkToken(token);
            bookInfo = bookService.getBookInfoByBookID(bookID);
        } catch (Exception e) {
            return JsonTool.returnPackage("error",e.getMessage(),null);
        }
        return JsonTool.returnPackage("success",null,bookInfo);
    }
}