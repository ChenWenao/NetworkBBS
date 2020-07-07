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
    public User getUserById(int userId) {
        return userRepository.selectUserById(userId);
    }

    //修改个人信息
    public boolean modifyUser(User user) {
        return userRepository.modifyUser(user);
    }

    //登录
    public User login(String userCode, String userPassword) {
        return userRepository.findUser(userCode,userPassword);
    }
}
