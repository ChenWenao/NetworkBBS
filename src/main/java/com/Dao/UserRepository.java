package com.Dao;

import com.Bean.User;
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
    public User selectUserById(int userId) {
        try {
            List<User> users = template.query("select * from User where userId =?", userRowMapper, userId);
            return users.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //修改个人信息
    public boolean modifyUser(User modifyUser) {
        try {
            template.update("update User set " +
                            "userName=?," +
                            "userIcon=?," +
                            "userPassword?," +
                            "userPhoneNumber=?," +
                            "userSecurityCode=?" +
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
