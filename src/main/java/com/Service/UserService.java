package com.Service;

import com.Bean.User;
import com.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //注册
    public boolean addNewUser(User newUser) {
        return userRepository.insertUser(newUser);
    }

    //注销
    public boolean removeUser(int userId) {
        return userRepository.deleteUser(userId);
    }

    //封禁
    public boolean bannedUser(int userId) {
        return userRepository.bannedUser(userId);
    }

    //修改个人信息
    public boolean modifyUser(User user) {
        return userRepository.modifyUser(user);
    }

    //找回密码验证
    public User resetPasswordCheck(String userCode, String userSecurityCode) {
        return userRepository.findResetUser(userCode, userSecurityCode);
    }

    //修改密码
    public boolean modifyPassword(User modifyUser) {
        return userRepository.modifyPassword(modifyUser);
    }

    //用户查询
    //userId
    public User getUserById(int userId) {
        return userRepository.selectUserById(userId);
    }

    //userCode
    public User getUserByCode(String userCode) {
        return userRepository.selectUserByCode(userCode);
    }

    //userName
    public User getUserByName(String userName) {
        return userRepository.selectUserByName(userName);
    }

    //登录
    public User login(String userCode, String userPassword) {
        return userRepository.findUser(userCode, userPassword);
    }
}
