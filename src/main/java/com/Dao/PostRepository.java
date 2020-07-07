package com.Dao;


import com.Bean.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    @Autowired
    private JdbcTemplate template;
    private PostRowMapper postRowMapper = new PostRowMapper();

    //增
    public boolean insertPost(String postTitle, String postContent, int postOwnerId, int postComId) {
        try {
            template.update("insert into post(postTitle," +
                    "postContent," +
                    "postOwnerId," +
                    "postComId) values (?,?,?,?)", postTitle, postContent, postOwnerId, postComId);
            template.update("update community set communitySize = communitySize+1 where communityId=?;", postComId);
            return true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //删
    public boolean deletePost(int postId, int postComId) {
        try {
            template.update("delete from post where postId=?", postId);
            template.update("update community set communitySize=communitySize-1 where communityId=?", postComId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    public Post selectPostById(int postId) {
        try {
            List<Post> posts = template.query("select * from post,user,community where postOwnerId=userId and postComId=communityId and postId=? ", postRowMapper, postId);
            return posts.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
