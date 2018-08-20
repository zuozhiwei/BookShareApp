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

    void uploadBook(HashMap<String,Object> param);

    HashMap<String,Object> getOwnerByBookID(HashMap<String,Object> paramGetOwner);

    void borrowBook(HashMap<String,Object> param);

    int returnBook(HashMap<String,Object> param);

    HashMap<String,Object> checkBookStatus(HashMap<String,Object> param);

    List<HashMap<String,Object>> getBorrowIngList();

    List<HashMap<String,Object>> getOutBookRecord(HashMap<String,Object> param);

    List<HashMap<String,Object>> getBorrowBookRecord(HashMap<String,Object> param);

    void reBorrow(HashMap<String,Object> param);

    HashMap<String,Object> getRYKey();

    List<HashMap<String,Object>> getUserList(HashMap<String,Object> param);
}