package com.Controller;

import com.Bean.Post;
import com.Bean.User;
import com.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    //增
    //新建贴
    //要求传入一个表单：newPost
    //表单要求包括字段：postTitle，postContent，postComId
    //处理逻辑，当前登录的用户在postComId吧发布一条帖子
    @PostMapping("Post/newPost")
    public boolean newPost(HttpSession session, @ModelAttribute(value = "newPost")Post newPost){

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        return postService.addNewPost(newPost.getPostTitle(),newPost.getPostContent(),((User) session.getAttribute("loginUser")).getUserId(),newPost.getPostComId());
    }

    //删帖
    //贴主删帖，或者管理员封禁贴时调用。
    //调用该接口时，后台会做身份核验，若登录的用户不是贴主，或者用户不是管理员，则会返回false
    //需要传入的值：postId
    @GetMapping("Post/dropPost/{postId}")
    public boolean dropPost(HttpSession session, @PathVariable("postId")int postId){

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------


        Post post_del=postService.getPostById(postId);
        if (post_del==null)
            return false;
        if(post_del.getPostOwnerId()==((User) session.getAttribute("loginUser")).getUserId()||((User) session.getAttribute("loginUser")).getUserLevel()==0){
            return postService.removePost(post_del.getPostId(),post_del.getPostComId());
        }else {
            return false;
        }
    }



}
