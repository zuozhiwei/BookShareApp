package com.hgo.book.dao;

import java.util.HashMap;
import java.util.List;

public interface IUserDao {

    List<HashMap<String,Object>> selectUser();

    HashMap<String, Object> checkLogin(HashMap<String, Object> param);
}