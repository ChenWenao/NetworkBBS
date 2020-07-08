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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private ToolService toolService = new ToolService();

    //通过id查询用户信息，需传入userId
    @GetMapping("User/userById/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    //通过name查询用户信息，需传入userName
    @GetMapping("User/userByName/{userName}")
    public User getUserByName(@PathVariable("userName") String userName) {
        return userService.getUserByName(userName);
    }

    //修改个人信息
    //传入modifyUser和头像，包含userId、userName、userPassword、userPhoneNumber、userSecurityCode
    @PostMapping("User/modifyUser")
    public String modifyUser(HttpSession session, @RequestParam("userImg") MultipartFile userImg, @ModelAttribute(value = "modifyUser") User modifyUser) {
        String msg = "";
        User bfUser = userService.getUserById(modifyUser.getUserId());
        //判断用户Id是否存在
        if (bfUser != null) {
            //判断用户名是否已存在
            if(userService.getUserByName(modifyUser.getUserName()) != null) {
                msg += "用户名已被占用，信息修改失败！";
            } else {
                //删除旧头像
                toolService.deleteFile(bfUser.getUserIcon());
                modifyUser.setUserIcon(toolService.FileToURL(userImg, "user"));
                userService.modifyUser(modifyUser);
                msg += "信息修改成功！";
            }
            return msg;
        }
        return "信息修改失败！";
    }

    //注册
    //传入newUser和头像，包含userName、userPassword、userPhoneNumber、userSecurityCode、userLevel
    @PostMapping("User/newUser")
    public String addNewUser(@RequestParam("userImg") MultipartFile userImg, @ModelAttribute(value = "newUser") User newUser) {
        //生成账号
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String code = formatter.format(new Date(System.currentTimeMillis()));//code开头为日期
        code += newUser.getUserPhoneNumber().substring(newUser.getUserPhoneNumber().length() - 8);//code接下来为手机号后八位
        //管理员尾数为0，用户尾数为1
        if ("0".equals(newUser.getUserLevel()))
            code += "0";
        else
            code += "1";
        newUser.setUserCode(code);
        //判断用户名是否已存在
        if(userService.getUserByName(newUser.getUserName()) != null) {
                return "用户名已被占用，注册失败！";
        } else {
            newUser.setUserIcon(toolService.FileToURL(userImg, "user"));
            if (userService.addNewUser(newUser)) {
                return "用户注册成功！";
            } else {
                userService.removeUser(newUser.getUserId());
            }
        }
        return "注册失败！";
    }

    //注销用户，需传入userId
    @PostMapping("User/logoutUser")
    public boolean logoutUser(@RequestBody List<Integer> userIds) {
        for (int userId : userIds) {
            if (!userService.removeUser(userId)) {
                return false;
            }
        }
        return true;
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
            mav.setViewName("redirect:User");
        } else {
            mav.setViewName("redirect:User/login");
        }
        return mav;
    }
}
