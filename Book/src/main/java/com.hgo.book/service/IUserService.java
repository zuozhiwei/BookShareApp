package com.hgo.book.service;

import java.util.HashMap;
import java.util.List;

public interface IUserService {

    public List<HashMap<String,Object>> selectUser();

    public String checkLogin(String mobile, String password) throws Exception;
}