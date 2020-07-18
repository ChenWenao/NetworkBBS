package com.Service;

import com.Bean.Comment;
import com.Dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    //增
    public boolean addNewComment(Comment comment){
        if (comment.getCommentReplyName()==null)
            return commentRepository.insertComment(comment);
        else
            return commentRepository.insertReply(comment);
    }



    //删除
    public boolean dropComment(int commentId){
        return commentRepository.deleteComment(commentId);
    }



    //查
    public Comment getCommentById(int commentId){
        return commentRepository.selectCommentById(commentId);
    }




}
