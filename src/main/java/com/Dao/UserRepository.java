package com.Dao;

import com.Bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate template;
    private UserRowMapper userRowMapper=new UserRowMapper();

    //用户查询
    //id
    public User selectUserById(int userId) {
        try {
            List<User> users = template.query("select * from User where userId =?", userRowMapper, userId);
            return users.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    //name
    public User selectUserByName(String userName) {
        try {
            List<User> users = template.query("select * from User where userName =?", userRowMapper, userName);
            return users.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //修改个人信息
    public boolean modifyUser(User modifyUser) {
        try {
            template.update("update User set userName=?," +
                            "userIcon=?," +
                            "userPassword=?," +
                            "userPhoneNumber=?," +
                            "userSecurityCode=? " +
                            "where userId=?"
                    , modifyUser.getUserName()
                    , modifyUser.getUserIcon()
                    , modifyUser.getUserPassword()
                    , modifyUser.getUserPhoneNumber()
                    , modifyUser.getUserSecurityCode()
                    , modifyUser.getUserId()
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //注册
    public boolean insertUser(User newUser) {
        try {
            template.update("insert into User(userName,userIcon,userPassword,userPhoneNumber,userSecurityCode,userLevel) values (?,?,?,?,?,?)"
                    , newUser.getUserName()
                    , newUser.getUserIcon()
                    , newUser.getUserPassword()
                    , newUser.getUserPhoneNumber()
                    , newUser.getUserSecurityCode()
                    , newUser.getUserLevel()
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //注销
    public boolean deleteUser(int userId) {
        try {
            //删除用户创建的帖子
            template.update("delete from Post where postOwnerId=?", userId);
            //删除用户创建的吧
            template.update("delete from Community where communityOwnerId=?", userId);
            //删除关联表
            template.update("delete from User_community where userId=?", userId);
            //删除用户表
            template.update("delete from User where userId=?", userId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //登录
    public User findUser(String userCode, String userPassword) {
        try {
            List<User> users = template.query("select * from User where userCode =? and userPassword=?", userRowMapper, userCode, userPassword);
            return users.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
