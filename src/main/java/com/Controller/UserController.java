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

    //注册
    //传入头像userImg和表单newUser，表单包含userName、userPassword、userPhoneNumber、userSecurityCode、userLevel
    @PostMapping("User/newUser")
    public String addNewUser(@RequestParam("userImg") MultipartFile userImg, @ModelAttribute(value = "newUser") User newUser) throws NoSuchAlgorithmException {
        //判断用户名是否已存在
        if (userService.getUserByName(newUser.getUserName()) != null) {
            return "用户名已被占用，注册失败！";
        } else {
            //生成账号
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            //code开头为日期
            String code = formatter.format(new Date(System.currentTimeMillis()));
            //接下来为手机号后2位
            code += newUser.getUserPhoneNumber().substring(newUser.getUserPhoneNumber().length() - 2);
            //管理员尾数为0，用户尾数为1
            if (0 == newUser.getUserLevel())
                code += "0";
            else
                code += "1";
            newUser.setUserCode(code);
            newUser.setUserIcon(toolService.FileToURL(userImg, "user"));
            //加密密码
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(newUser.getUserPassword().getBytes());
            String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
            newUser.setUserPassword(password_MD5);
            if (userService.addNewUser(newUser)) {
                return "用户注册成功！";
            } else {
                userService.removeUser(newUser.getUserId());
            }
        }
        return "注册失败！";
    }

    //注销用户(普通用户)
    //传入表单userSecurityCode
    @PostMapping("User/logoutUser")
    public boolean logoutUser(HttpSession session, @ModelAttribute(value = "userSecurityCode") String userSecurityCode) {
        User logoutUser = (User) session.getAttribute("loginUser");
        //安全验证，判断安全码与账号是否匹配
        if (userSecurityCode.equals(logoutUser.getUserSecurityCode())) {
            if (userService.removeUser(logoutUser.getUserId())) {
                return true;
            }
        }
        return false;
    }

    //封禁用户(管理员)
    //传入要封禁用户的userId
    @GetMapping("User/bannedUser/{userId}")
    public boolean bannedUser(HttpSession session, @PathVariable("userId") int userId) {
        User adminUser = (User) session.getAttribute("loginUser");
        //身份验证
        if (0 == adminUser.getUserLevel()) {
            if (userService.bannedUser(userId)) {
                return true;
            }
        }
        return false;
    }

    //修改个人信息
    //传入头像userImg和表单modifyUser，表单包含userName、userPhoneNumber
    @PostMapping("User/modifyUser")
    public String modifyUser(HttpSession session, @RequestParam("userImg") MultipartFile userImg, @ModelAttribute(value = "modifyUser") User modifyUser) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(14);
        loginUser.setUserName("张三");
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------
        String msg = "";
        User lgUser = (User) session.getAttribute("loginUser");
        //判断用户名是否存在
        if (userService.getUserByName(modifyUser.getUserName()) != null) {
            //存在，判断用户Id是否相同(不改变用户名只改其它信息)
            if (userService.getUserByName(modifyUser.getUserName()).getUserId() == lgUser.getUserId()) {
                //id相同
                //删除旧头像
                toolService.deleteFile(userService.getUserByName(modifyUser.getUserName()).getUserIcon());
                modifyUser.setUserIcon(toolService.FileToURL(userImg, "user"));
                modifyUser.setUserId(lgUser.getUserId());
                //修改信息
                userService.modifyUser(modifyUser);
                msg += "信息修改成功！";
            } else {
                //id不同，用户名重复
                msg += "用户名已被占用，信息修改失败！";
            }
        } else {
            //不存在，直接修改信息
            //删除旧头像
            toolService.deleteFile(userService.getUserByName(modifyUser.getUserName()).getUserIcon());
            modifyUser.setUserIcon(toolService.FileToURL(userImg, "user"));
            modifyUser.setUserId(lgUser.getUserId());
            //修改信息
            userService.modifyUser(modifyUser);
            msg += "信息修改成功！";
        }
        return msg;
    }

    //找回密码验证
    //成功会跳转到修改密码的页面，失败则会返回一个新的找回密码验证页面
    //传入表单resetUser，包含userCode、userSecurityCode
    @PostMapping("User/resetPasswordCheck")
    public boolean resetPasswordCheck(HttpSession session, @ModelAttribute(value = "resetUser") User resetUser) {
        User user_find = userService.resetPasswordCheck(resetUser.getUserCode(), resetUser.getUserSecurityCode());
        //验证要修改密码的账号与安全码
        if (user_find != null) {
            session.setAttribute("resetUser", user_find);
            return true;
        }
        return false;
    }

    //重置密码
    //传入表单newPassword
    @PostMapping("User/resetPassword")
    public boolean resetPassword(HttpSession session, @ModelAttribute(value = "newPassword") String newPassword) throws NoSuchAlgorithmException {
        //拿到验证时添加的用户
        ModelAndView mav = new ModelAndView();
        User resetUser = (User) session.getAttribute("resetUser");
        //拿完用户后关闭session
        session.removeAttribute("resetUser");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(newPassword.getBytes());
        String newPassword_MD5 = new BigInteger(1, md5.digest()).toString(16);
        resetUser.setUserPassword(newPassword_MD5);
        if (userService.modifyPassword(resetUser)) {
            return true;
        } else {
            return false;
        }
    }

    //通过id查询用户信息
    //传入userId
    @GetMapping("User/userById/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    //通过code查询用户信息
    //传入userCode
    @GetMapping("User/userByCode/{userCode}")
    public User getUserByCode(@PathVariable("userCode") String userCode) {
        return userService.getUserByCode(userCode);
    }

    //通过name查询用户信息
    //传入userName
    @GetMapping("User/userByName/{userName}")
    public User getUserByName(@PathVariable("userName") String userName) {
        return userService.getUserByName(userName);
    }

    //登录
    //传入表单loginUser，包含userCode,userPassword
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
            //判断账户状态是否被封禁，isEnable为1表示正常，0表示被封禁
            if (1 == user_find.getIsEnable()) {
                session.setAttribute("loginUser", user_find);
                User user = (User) session.getAttribute("loginUser");
                mav.setViewName("");
            }
        } else {
            mav.setViewName("");
        }
        return mav;
    }
}
