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
                            "userPhoneNumber=? " +
                            "where userId=?"
                    , modifyUser.getUserName()
                    , modifyUser.getUserIcon()
                    , modifyUser.getUserPhoneNumber()
                    , modifyUser.getUserId()
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //修改密码
    public boolean modifyPassword(User modifyUser) {
        try {
            template.update("update User set " +
                            "userPassword=? " +
                            "where userCode=? "
                    , modifyUser.getUserPassword()
                    , modifyUser.getUserCode());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //找回密码
    public User findResetUser(String userCode, String userSecurityCode) {
        try {
            List<User> users = template.query("select * from User where userCode =? and userSecurityCode=?"
                    , userRowMapper
                    , userCode
                    , userSecurityCode);
            return users.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //注册
    public boolean insertUser(User newUser) {
        try {
            template.update("insert into User(userName,userIcon,userPassword,userPhoneNumber,userSecurityCode,userLevel,userCode,isEnable) values (?,?,?,?,?,?,?,1)"
                    , newUser.getUserName()
                    , newUser.getUserIcon()
                    , newUser.getUserPassword()
                    , newUser.getUserPhoneNumber()
                    , newUser.getUserSecurityCode()
                    , newUser.getUserLevel()
                    , newUser.getUserCode()
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //封禁
    public boolean bannedUser(User bannedUser) {
        try {
            template.update("update User set isEnable=? where userId=?"
                    , bannedUser.getIsEnable()
                    , bannedUser.getUserId()
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //注销(个人)
    public boolean deleteUser(int userId) {
        try {
            //删除回复该用户的评论
            //template.update("delete from Comment where commentId in (select commentOwnerId from User,Comment where commentReplyName=userName and userId=?)", userId);
            //删除用户的评论
            template.update("delete from Comment where commentOwnerId=?", userId);
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
