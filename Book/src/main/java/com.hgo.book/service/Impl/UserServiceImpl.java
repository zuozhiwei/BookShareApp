package com.hgo.book.service.Impl;

import com.hgo.book.dao.IUserDao;
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

    @Override
    public String checkLogin(String mobile, String password) throws Exception {

        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("mobile",mobile);
        param.put("password",password);
        HashMap<String,Object> result = this.userDao.checkLogin(param);
        if (null == result || !result.containsKey("token")) {
            throw new Exception("用户名或错误");
        }
        return result.get("token").toString();
    }
}