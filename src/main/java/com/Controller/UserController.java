package com.Controller;

import com.Bean.*;
import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

public class UserController {
    @Autowired
    private UserService userService;

    //通过id查询用户信息，需传入userId
    @GetMapping("User/userById/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

}
