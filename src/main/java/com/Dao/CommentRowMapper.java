package com.Dao;

import com.Bean.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Comment comment = new Comment();
        comment.setCommentId(resultSet.getInt("commentId"));
        comment.setCommentContent(resultSet.getString("commentContent"));
        comment.setCommentPostId(resultSet.getInt("commentPostId"));
        comment.setCommentOwnerId(resultSet.getInt("commentOwnerId"));
        comment.setCommentOwnerName(resultSet.getString("userName"));
        comment.setCommentReplyName(resultSet.getString("commentReplyName"));
        comment.setCommentReplyContent(resultSet.getString("commentReplyContent"));
        comment.setCommentTime(resultSet.getTimestamp("commentTime"));
        comment.setCommentRead(resultSet.getBoolean("commentRead"));
        return comment;
    }
}
