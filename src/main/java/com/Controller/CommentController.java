package com.Controller;


import com.Bean.Comment;
import com.Bean.Community;
import com.Bean.User;
import com.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    // commentReplyName：若该评论是回复另外一条评论的，则传入该字段的值为另外一条评论的评论用户名，否则不传入。
    // commentReplyContent：该评论如果是回复另外一条评论的，则该字段的值为另外一条评论的评论内容，否则不传入。
    //理解起来就是，当前登录的用户，在commentPostId下发布了一条  “评论” / ”对commentReplyId的回复”  内容为commentContent
    @PostMapping("Comment/newComment")
    public boolean newComment(HttpSession session, @ModelAttribute(value = "newComment") Comment newComment) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        loginUser.setUserName("陈文奥");
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------


        //设置ownerId
        newComment.setCommentOwnerId(((User) session.getAttribute("loginUser")).getUserId());
        return commentService.addNewComment(newComment);
    }

    //删评

    //删除评论，不会连带删除其他信息。
    @GetMapping("Comment/deleteComment/{commentId}")
    public boolean dropComment(HttpSession session, @PathVariable("commentId") int commentId) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        loginUser.setUserName("陈文奥");
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        Comment ser_comment = commentService.getCommentById(commentId);

        if (ser_comment.getCommentOwnerId() == ((User) session.getAttribute("loginUser")).getUserId() || ((User) session.getAttribute("loginUser")).getUserLevel() == 0) {
            return commentService.dropComment(commentId);
        }
        return false;
    }


    //查
    //传入postId，获取某个帖子的所有评论
    @GetMapping("Comment/getComment/{postId}")
    public List<Comment> getComments(@PathVariable("postId") int postId) {
        return commentService.getCommentsByPostId(postId);
    }

    //获取自己发表的评论
    //采用传统的url方式（url后加问号，然后传值）
    //传入一个字段：read  ，
    // 传入的read值为1，可查询已读的评论，传入read为0，可查询未读的评论
    //不传入值时默认获取用户所有的评论。
    @GetMapping("Comment/myComment")
    public List<Comment> getMyComments(HttpSession session, @RequestParam(name = "read", defaultValue = "2") int read) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        loginUser.setUserName("陈文奥");
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        return commentService.getMyComments(read, ((User) session.getAttribute("loginUser")).getUserId());

    }


}
