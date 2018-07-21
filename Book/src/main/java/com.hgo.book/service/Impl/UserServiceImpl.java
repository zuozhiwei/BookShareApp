package com.hgo.book.service.Impl;

import com.hgo.book.dao.IUserDao;
import com.hgo.book.service.IUserService;
import com.hgo.book.tool.Tool;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
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

    /**
     * 用户登录
     * @param mobile 手机号
     * @param password 密码
     * @return
     * @throws Exception
     */
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

    /**
     * 更新token
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    @Override
    public String updateToken(String mobile) throws Exception {
        HashMap<String,Object> param = new HashMap<String,Object>();
        String nowTime = System.currentTimeMillis()+"";
        String token = DigestUtils.md5DigestAsHex((mobile+nowTime).getBytes());
        param.put("token",token);
        param.put("mobile",mobile);
        int result = this.userDao.updateToken(param);
        if (result == 0) {
            throw new Exception("更新token出错");
        }
        return token;
    }

    /**
     * 注册
     * @param mobile 手机号
     * @param password 密码
     */
    @Override
    public void register(String mobile, String password) throws Exception {
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("mobile",mobile);
        param.put("password",password);
        int result = 0;
        try {
            result = this.userDao.register(param);
        } catch (Exception e) {
            throw new Exception("手机号已注册，请登录");
        }
        if (result == 0) {
            throw new Exception("注册失败，请重试");
        }
    }

    /**
     * 检查手机号码是否存在
     * @param mobile
     * @throws Exception
     */
    @Override
    public void checkUser(String mobile) throws Exception {
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("mobile",mobile);
        HashMap result = this.userDao.checkUser(param);
        if (null != result && result.containsKey("id")) {
            throw new Exception("用户已存在");
        }
    }

    /**
     * 检查token是否匹配
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String,Object> checkToken(String token) throws Exception {
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("token",token);
        HashMap<String,Object> userInfo = this.userDao.checkToken(param);
        if (null == userInfo || "".equals(userInfo)) {
            throw new Exception("token失效，请重新登录");
        }
        return userInfo;
    }

    /**
     * 获取图书列表
     * @param bookName
     * @param borrowStatus
     * @return
     */
    @Override
    public List<HashMap<String, Object>> getBookList(String bookName, String borrowStatus, String token) {
        HashMap<String,Object> param = new HashMap<String,Object>();
        Tool.hashMapPutTool(param,"bookName",bookName);
        Tool.hashMapPutTool(param,"token",token);
        List<HashMap<String, Object>> bookList = this.userDao.getBookList(param);
        List<HashMap<String, Object>> borrowIngList =this.userDao.getBorrowIngList();
        int len1 = bookList.size();
        int len2 = borrowIngList.size();
        for (int i = 0; i<len1; i++) {
            int isBorrow = 0;
            for (int j = 0; j<len2; j++) {
                if (bookList.get(i).get("book_id").toString().equals(borrowIngList.get(j).get("book_id").toString())) {
                    isBorrow = 1;
                }
            }
            bookList.get(i).put("isBorrow",isBorrow);
        }
        if (null != borrowStatus) {
            for (int i = 0;i < len1;i++) {
                if (!bookList.get(i).get("isBorrow").toString().equals(borrowStatus)) {
                    bookList.remove(i);
                }
            }
        }
        return bookList;
    }

    /**
     * 上传图书
     * @param token
     * @param bookName
     * @param bookAuthor
     * @param bookDesc
     * @param bookPrice
     * @param bookLabel
     * @param bookMessage
     * @param bookCoverUrl
     * @throws Exception
     */
    @Override
    public void uploadBook(String token, String bookName, String bookAuthor, String bookDesc, String bookPrice, String bookLabel, String bookMessage, String bookCoverUrl) throws Exception {
        String userID = "";
        HashMap<String,Object> paramUserID = new HashMap<String,Object>();
        paramUserID.put("token",token);
        HashMap<String,Object> userInfo = this.userDao.checkToken(paramUserID);
        if (null == userInfo || "".equals(userInfo)) {
            throw new Exception("token失效，请重新登录");
        } else {
            userID = userInfo.get("id").toString();
        }

        HashMap<String,Object> param = new HashMap<String,Object>();
        Tool.hashMapPutTool(param,"userID",userID);
        Tool.hashMapPutTool(param,"bookName",bookName);
        Tool.hashMapPutTool(param,"bookAuthor",bookAuthor);
        Tool.hashMapPutTool(param,"bookDesc",bookDesc);
        Tool.hashMapPutTool(param,"bookPrice",bookPrice);
        Tool.hashMapPutTool(param,"bookLabel",bookLabel);
        Tool.hashMapPutTool(param,"bookMessage",bookMessage);
        Tool.hashMapPutTool(param,"bookCoverUrl",bookCoverUrl);
        this.userDao.uploadBook(param);
    }

    /**
     * 借书
     * @param token
     * @param bookID
     * @throws Exception
     */
    @Override
    public void borrowBook(String token, String bookID) throws Exception {
        String userID = "";
        HashMap<String,Object> paramUserID = new HashMap<String,Object>();
        paramUserID.put("token",token);
        HashMap<String,Object> userInfo = this.userDao.checkToken(paramUserID);
        if (null == userInfo || "".equals(userInfo)) {
            throw new Exception("token失效，请重新登录");
        } else {
            userID = userInfo.get("id").toString();
        }

        HashMap<String,Object> paramGetOwner = new HashMap<String,Object>();
        Tool.hashMapPutTool(paramGetOwner,"bookID",bookID);
        HashMap<String,Object> ownerInfo = this.userDao.getOwnerByBookID(paramGetOwner);
        if (null == ownerInfo) {
            throw new Exception("根据图书id找不到图书主人");
        }
        String ownerID = ownerInfo.get("user_id").toString();

        if (ownerID.equals(userID)) {
            throw new Exception("不能借自己的书");
        }

        HashMap<String,Object> param = new HashMap<String,Object>();
        Tool.hashMapPutTool(param,"bookID",bookID);
        Tool.hashMapPutTool(param,"userID",userID);
        Tool.hashMapPutTool(param,"ownerID",ownerID);
        HashMap<String,Object> bookStatus = this.userDao.checkBookStatus(param);
        if (null != bookStatus && !"".equals(bookStatus.get("id"))) {
            throw new Exception("书已借出，请查看其它图书");
        }
        this.userDao.borrowBook(param);
    }

    /**
     * 还书
     * @param token
     * @param bookID
     * @param recordID
     * @throws Exception
     */
    @Override
    public void returnBook(String token, String bookID, String recordID) throws Exception {
        if (null == bookID && null == recordID) {
            throw new Exception("bookID、recordID至少输入一个参数");
        }
        if ( ("".equals(bookID) && null == recordID) || ("".equals(recordID) && null == bookID) ) {
            throw new Exception("参数不能都为空");
        }
        HashMap<String,Object> param = new HashMap<String,Object>();
        Tool.hashMapPutTool(param,"bookID",bookID);
        Tool.hashMapPutTool(param,"recordID",recordID);
        int result = this.userDao.returnBook(param);
        if (result == 0) {
            throw new Exception("还书失败（或图书已还），请刷新重试");
        }
    }

}