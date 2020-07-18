package com.Service;

import com.Bean.User;
import com.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //用户查询
    //userId
    public User getUserById(int userId) {
        return userRepository.selectUserById(userId);
    }

    //userName
    public User getUserByName(String userName) {
        return userRepository.selectUserByName(userName);
    }

    //修改个人信息
    public boolean modifyUser(User user) {
        return userRepository.modifyUser(user);
    }

    //修改密码
    public boolean modifyPassword(User modifyUser) {
        return userRepository.modifyPassword(modifyUser);
    }

    //找回密码
    public User resetPasswordCheck(String userCode, String userSecurityCode) {
        return userRepository.findResetUser(userCode, userSecurityCode);
    }

    //注册
    public boolean addNewUser(User newUser) {
        return userRepository.insertUser(newUser);
    }

    //封禁
    public boolean bannedUser(User bannedUser) {
        return userRepository.bannedUser(bannedUser);
    }

    //注销
    public boolean removeUser(int userId) {
        return userRepository.deleteUser(userId);
    }

    //登录
    public User login(String userCode, String userPassword) {
        return userRepository.findUser(userCode, userPassword);
    }
}
