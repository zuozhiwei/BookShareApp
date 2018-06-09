package com.hgo.book.service;

import com.hgo.book.model.User;

import java.util.HashMap;
import java.util.List;

public interface IUserService {

    public List<HashMap<String,Object>> selectUser();

}