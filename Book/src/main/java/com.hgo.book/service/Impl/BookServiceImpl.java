package com.hgo.book.service.Impl;

import com.hgo.book.dao.IBookDao;
import com.hgo.book.dao.IUserDao;
import com.hgo.book.service.IBookService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements IBookService {

    @Resource
    private IBookDao bookDao;

    @Override
    public HashMap<String, Object> getBookInfoByBookID(String bookID) {
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("bookID",bookID);
        HashMap<String,Object> bookInfo = this.bookDao.getBookInfoByBookID(param);
        return bookInfo;
    }
}