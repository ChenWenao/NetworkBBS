package com.Controller;

import com.Bean.Community;
import com.Bean.User;
import com.Service.CommunityService;
import com.Service.ToolService;
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
    private ToolService toolService = new ToolService();
    //增

    //新建吧
    //要求传入表单和一个图片，
    //表单名：newCommunity
    //图片名：communityImg
    //表单需要包括字段：communityName，communityIntroduction
    @PostMapping("Community/newCommunity")
    public boolean newCommunity(HttpSession session, @ModelAttribute(value = "newCommunity") Community newCommunity, @RequestParam("communityImg") MultipartFile communityImg) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        //判断是否传入图片。
        if (communityImg.isEmpty())
            return false;
        newCommunity.setCommunityIcon(toolService.FileToURL(communityImg, "community"));
        newCommunity.setCommunityOwnerId(((User) session.getAttribute("loginUser")).getUserId());
        return communityService.addNewCommunity(newCommunity);
    }

    //删
    //删除吧
    //吧主放弃吧，或者管理员封禁吧时调用。
    //调用该接口时，后台会做身份核验，若登录的用户不是该吧的吧主，或者用户不是管理员，则会返回false
    //需要传入的值：communityId
    @PostMapping("Community/dropCommunity")
    public boolean dropCommunity(HttpSession session, @ModelAttribute(value = "communityId") int communityId) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------


        Community community = communityService.getCommunityById(communityId);
        if (community.getCommunityOwnerId() == ((User) session.getAttribute("loginUser")).getUserId() || ((User) session.getAttribute("loginUser")).getUserLevel() == 0) {
            toolService.deleteFile(community.getCommunityIcon());
            communityService.removeCommunity(community.getCommunityId());
            return true;
        } else {
            return false;//用户不是吧主，或者用户不是管理员
        }
    }

    //改
    //修改吧的相关信息
    //仅支持修改吧名，吧简介，吧主。
    //调用该接口时，后台会做身份验证，若修改人不是吧的吧主或者管理员，则会返回false
    //需要传入表单：modifyCommunity和吧头像，表单包含字段communityId,communityName,communityIntroduction
    //ps：就算值没有改动，也要把旧值传入
    @PostMapping("Community/modifyCommunity")
    public boolean modifyCommunity(HttpSession session, @ModelAttribute(value = "modifyCommunity") Community modifyCommunity, @RequestParam("communityImg") MultipartFile communityImg) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------
        //删除旧的图片
        Community community = communityService.getCommunityById(modifyCommunity.getCommunityId());
        toolService.deleteFile(community.getCommunityIcon());

        //修改数据
        modifyCommunity.setCommunityIcon(toolService.FileToURL(communityImg, "community"));
        communityService.modifyCommunity(modifyCommunity);
        return true;
    }

}
