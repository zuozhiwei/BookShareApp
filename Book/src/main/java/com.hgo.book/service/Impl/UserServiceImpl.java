package com.hgo.book.service.Impl;

import com.hgo.book.dao.IUserDao;
import com.hgo.book.model.User;
import com.hgo.book.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public List<HashMap<String,Object>> selectUser() {
        return this.userDao.selectUser();
    }
}