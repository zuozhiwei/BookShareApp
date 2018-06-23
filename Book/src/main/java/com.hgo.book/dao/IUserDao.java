package com.hgo.book.dao;

import java.util.HashMap;
import java.util.List;

public interface IUserDao {

    List<HashMap<String,Object>> selectUser();

    HashMap<String, Object> checkLogin(HashMap<String, Object> param);

    int updateToken(HashMap<String,Object> param);

    HashMap checkUser(HashMap<String,Object> param);

    int register(HashMap<String,Object> param);

    HashMap<String,Object> checkToken(HashMap<String,Object> param);

    List<HashMap<String,Object>> getBookList(HashMap<String,Object> param);
}