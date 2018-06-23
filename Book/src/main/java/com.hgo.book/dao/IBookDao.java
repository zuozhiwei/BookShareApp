package com.hgo.book.dao;

import java.util.HashMap;
import java.util.List;

public interface IBookDao {

    HashMap<String,Object> getBookInfoByBookID(HashMap<String,Object> param);
}