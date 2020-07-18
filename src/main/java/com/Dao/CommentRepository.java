package com.Dao;


import com.Bean.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
    @Autowired
    private JdbcTemplate template;
    private CommentRowMapper commentRowMapper = new CommentRowMapper();


    //增
    //评论
    public boolean insertComment(Comment comment) {
        try {
            template.update("insert into comment(commentContent,commentPostId,commentOwnerId) values (?,?,?)",
                    comment.getCommentContent(),
                    comment.getCommentPostId(),
                    comment.getCommentOwnerId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //回复
    public boolean insertReply(Comment comment) {
        try {
            template.update("insert into comment(commentContent,commentPostId,commentOwnerId,commentReplyName,commentReplyContent) values (?,?,?)",
                    comment.getCommentContent(),
                    comment.getCommentPostId(),
                    comment.getCommentOwnerId(),
                    comment.getCommentReplyName(),
                    comment.getCommentReplyContent());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    //删
    public boolean deleteComment(int commentId) {
        try {
            template.update("update post set postReplies=postReplies-1 where postId in (select commentPostId from comment where commentId=?)",commentId);
            template.update("delete from comment where commentId=?",commentId);
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }


    //查
    public Comment selectCommentById(int commentId){
        try {
            List<Comment> comments=template.query("select * from comment,user where userId=commentOwnerId and commentId=?",commentRowMapper,commentId);
            return comments.get(0);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }


}
