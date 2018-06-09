package com.hgo.book.dao;

import com.hgo.book.model.User;

import java.util.HashMap;
import java.util.List;

public interface IUserDao {

    List<HashMap<String,Object>> selectUser();

}