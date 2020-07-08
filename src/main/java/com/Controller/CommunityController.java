package com.Controller;

import com.Bean.Community;
import com.Bean.User;
import com.Service.CommunityService;
import com.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        //判断是否传入图片。
        if (communityImg.isEmpty())
            return false;
        newCommunity.setCommunityIcon(toolService.FileToURL(communityImg, "community"));
        newCommunity.setCommunityOwnerId(((User) session.getAttribute("loginUser")).getUserId());
        if (communityService.addNewCommunity(newCommunity)) {
            return true;
        } else {
            toolService.deleteFile(newCommunity.getCommunityIcon());
            return false;
        }
    }

    //关注吧
    //处理逻辑：当前登录的用户关注传入的communityId对应的吧
    @GetMapping("Community/collectCommunity/{communityId}")
    public boolean collectCommunity(HttpSession session, @PathVariable("communityId") int communityId) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        return communityService.collectCommunityUser(((User) session.getAttribute("loginUser")).getUserId(), communityId);
    }

    //取关
    @GetMapping("Community/unCollectCommunity/{communityId}")
    public boolean unCollectCommunity(HttpSession session, @PathVariable("communityId") int communityId) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        return communityService.unCollectCommunityUser(((User) session.getAttribute("loginUser")).getUserId(), communityId);
    }

    //删
    //删除吧
    //吧主放弃吧，或者管理员封禁吧时调用。
    //调用该接口时，后台会做身份核验，若登录的用户不是该吧的吧主，或者用户不是管理员，则会返回false
    //需要传入的值：communityId
    @GetMapping("Community/dropCommunity/{communityId}")
    public boolean dropCommunity(HttpSession session, @PathVariable("communityId") int communityId) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        Community community = communityService.getCommunityById(communityId);
        if(community==null)
            return false;
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
        loginUser.setUserLevel(0);
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

    //查
    //单查询（精准查询）
    @GetMapping("Community/communityById/{communityId}")
    public Community searchCommunity(@PathVariable("communityId") int communityId) {
        return communityService.getCommunityById(communityId);
    }

    //多查询
    //param：用于搜索，表示搜索哪个字段
    //qw：用于搜索，搜索param的字段中包括value的结果。
    //若param和value都为“all”，表示不定向搜索。
    //order_by：表示根据哪个字段排序。默认为 communityHeat
    //order：用于排序，为0表示正序，为1表示倒序。 默认为 正序
    //pageSize：表示分页页面大小。 默认为 5
    //page：表示查询第几页的数据。 默认为 1
    //若pageSize和page都为0，则不分页，返回所有数据。
    @GetMapping("Communities/search")
    public List<Community> searchCommunities(@RequestParam(name = "param",defaultValue = "communityName") String param, @RequestParam("qw") String value, @RequestParam(name="order_by",defaultValue = "communityHeat") String order_by, @RequestParam(name="order",defaultValue = "0") int order, @RequestParam(name="pageSize",defaultValue = "5") int pageSize, @RequestParam(name="page",defaultValue = "1") int page) {
        return communityService.getCommunities(param, value, order_by, order, pageSize, page);
    }
}
