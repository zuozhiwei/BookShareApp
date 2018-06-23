package com.hgo.book.service;

import java.util.HashMap;
import java.util.List;

public interface IBookService {

    HashMap<String,Object> getBookInfoByBookID(String bookID);
}