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
    private CommentRowMapper commentRowMapper=new CommentRowMapper();


    public boolean insertComment(Comment comment){
        try {
            template.update("insert into comment(commentContent,commentPostId,commentOwnerId) values (?,?,?)",
                    comment.getCommentContent(),
                    comment.getCommentPostId(),
                    comment.getCommentOwnerId());
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean insertReply(Comment comment){
        try {
            template.update("insert into comment(commentContent,commentPostId,commentOwnerId,commentReplyName,commentReplyContent) values (?,?,?)",
                    comment.getCommentContent(),
                    comment.getCommentPostId(),
                    comment.getCommentOwnerId(),
                    comment.getCommentReplyName(),
                    comment.getCommentReplyContent());
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }







}
