package com.Controller;

import com.Bean.Post;
import com.Bean.User;
import com.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
