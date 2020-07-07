package com.Controller;

import com.Bean.*;
import com.Service.UserService;
import com.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserController {
    @Autowired
    private UserService userService;
    private ToolService toolService = new ToolService();

    //通过id查询用户信息，需传入userId
    @GetMapping("User/userById/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    //修改个人信息
    //传入modifyUser和头像，包含userName、userPassword、userPhoneNumber、userSecurityCode
    @PostMapping("User/modifyUser")
    public String modifyUser(HttpSession session, @RequestParam("userImg") MultipartFile userImg, @ModelAttribute(value = "modifyUser") User modifyUser) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------
        String msg = "";
        User oldUser = userService.getUserById(modifyUser.getUserId());
        //判断用户Id是否存在
        if (oldUser != null) {
            //非空，判断用户名是否已存在
            if(modifyUser.getUserName() != null) {
                msg += "用户名已被占用，信息修改失败！";
            } else {
                //删除旧头像
                toolService.deleteFile(oldUser.getUserIcon());
                //修改信息
                modifyUser.setUserIcon(toolService.FileToURL(userImg, "user"));
                userService.modifyUser(modifyUser);
                msg += "信息修改成功！";
            }
            return msg;
        }
        else return "信息修改失败！";
    }

    //登录
    //传入userCode,userPassword
    @PostMapping("User/login")
    public ModelAndView login(HttpSession session, @ModelAttribute(value = "loginUser") User loginUser) throws NoSuchAlgorithmException {
        ModelAndView mav = new ModelAndView();//新建要返回的页面。
        //加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(loginUser.getUserPassword().getBytes());
        String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
        loginUser.setUserPassword(password_MD5);
        //验证登录
        User user_find = userService.login(loginUser.getUserCode(), loginUser.getUserPassword());
        if (user_find != null) {
            session.setAttribute("loginUser", user_find);
            User user =  (User)session.getAttribute("loginUser");
            mav.setViewName("redirect:/User");
        } else {
            mav.setViewName("redirect:/User/login");
        }
        return mav;
    }
}
