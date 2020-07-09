package com.Controller;


import com.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    //增
    //发表评论
    //传入一个表单：newComment
    //表单包含字段
    // commentContent：该评论的内容
    // commentPostId：该评论所在的帖子
    // commentReplyId：若该评论是回复另外一条评论的，则传入该字段的值为该评论所回复的评论的id，否则为空。
    //理解起来就是，当前登录的用户，在commentPostId下发布了一条  “评论” / ”对commentReplyId的回复”  内容为commentContent



}
