package com.Controller;

import com.Bean.Community;
import com.Bean.User;
import com.Service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    //增

    //新建吧
    //要求传入表单和一个图片，
    //表单名：newCommunity
    //图片名：communityImg
    //表单需要包括字段：communityName，communityIntroduction
    @PostMapping("Community/newCommunity")
    public boolean newCommunity(HttpSession session, @ModelAttribute(value = "newCommunity") Community newCommunity, @RequestParam("communityImg") MultipartFile communityImg) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser=new User();
        session.setAttribute("loginUser",loginUser);
        //---------------------------------------------------------------------------------

        //判断是否传入图片。
        if (communityImg.isEmpty())
            return false;


        return communityService.addNewCommunity(newCommunity);

    }

}
