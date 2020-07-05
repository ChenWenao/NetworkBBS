package com.Controller;

import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

public class UserController {
    @Autowired
    private UserService userService;

}
