package com.Dao;

import com.Bean.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post>{
    @Override
    public Post mapRow(ResultSet resultSet,int rowNum) throws SQLException {
        Post post=new Post();
        post.setPostId(resultSet.getInt("postId"));
        post.setPostTitle(resultSet.getString("postTitle"));
        post.setPostContent(resultSet.getString("postContent"));
        post.setPostTime(resultSet.getTimestamp("postTime"));
        post.setPostHeat(resultSet.getInt("postHeat"));
        post.setPostReplies(resultSet.getInt("postReplies"));
        post.setPostOwnerId(resultSet.getInt("postOwnerId"));
        post.setPostOwnerName(resultSet.getString("userName"));
        post.setPostComId(resultSet.getInt("postComId"));
        post.setPostComName(resultSet.getString("communityName"));
        return post;
    }
}
