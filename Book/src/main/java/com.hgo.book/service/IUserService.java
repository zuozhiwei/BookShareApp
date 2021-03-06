package com.hgo.book.service;

import java.util.HashMap;
import java.util.List;

public interface IUserService {


    String checkLogin(String mobile, String password) throws Exception;

    String updateToken(String mobile) throws Exception;

    void register(String mobile, String password, String ryToken) throws Exception;

    void checkUser(String mobile) throws Exception;

    HashMap<String,Object> checkToken(String token) throws Exception;

    List<HashMap<String,Object>> getBookList(String bookName, String borrowStatus, String token);

    void uploadBook(String token, String bookName, String bookAuthor, String bookDesc, String bookPrice, String bookLabel, String bookMessage, String bookCoverUrl) throws Exception;

    void borrowBook(String token, String bookID) throws Exception;

    void returnBook(String token, String bookID, String recordID) throws Exception;

    List<HashMap<String,Object>> getOutBookRecord(String bookName, String bookStatus, String token);

    List<HashMap<String,Object>> getBorrowBookRecord(String bookName, String bookStatus, String token);

    HashMap<String,Object> getRYKey();

    List<HashMap<String,Object>> getUserList(String token);
}