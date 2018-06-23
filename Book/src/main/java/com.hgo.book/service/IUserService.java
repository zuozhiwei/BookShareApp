package com.hgo.book.service;

import java.util.HashMap;
import java.util.List;

public interface IUserService {


    String checkLogin(String mobile, String password) throws Exception;

    String updateToken(String mobile) throws Exception;

    void register(String mobile, String password) throws Exception;

    void checkUser(String mobile) throws Exception;

    HashMap<String,Object> checkToken(String token) throws Exception;

    List<HashMap<String,Object>> getBookList(String bookName);
}