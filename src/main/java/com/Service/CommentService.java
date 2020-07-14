package com.Service;

import com.Bean.Comment;
import com.Dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public boolean addNewComment(Comment comment){
        if (comment.getCommentReplyName()==null)
            return commentRepository.insertComment(comment);
        else
            return commentRepository.insertReply(comment);
    }




}
