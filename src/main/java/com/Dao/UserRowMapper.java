package com.Dao;

import com.Bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet,int rowNum)throws SQLException{
        User user =new User();
        user.setUserId(resultSet.getInt("userId"));
        user.setUserName(resultSet.getString("userName"));
        user.setUserIcon(resultSet.getString("userIcon"));
        user.setUserCode(resultSet.getString("userCode"));
        user.setUserPassword(resultSet.getString("userPassword"));
        user.setUserPhoneNumber(resultSet.getString("userPhoneNumber"));
        user.setUserSecurityCode(resultSet.getString("userSecurityCode"));
        user.setUserRegistrationTime(resultSet.getTimestamp("userRegistrationTime"));
        user.setUserLevel(resultSet.getInt("userLevel"));
        user.setIsEnable(resultSet.getInt("isEnable"));
        return user;
    }
}
