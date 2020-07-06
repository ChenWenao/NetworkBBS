package com.Service;

import com.Bean.User;
import com.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(int userId) {
        return userRepository.selectUserById(userId);
    }

}
