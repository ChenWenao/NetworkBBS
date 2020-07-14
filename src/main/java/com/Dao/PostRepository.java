package com.Dao;


import com.Bean.Community;
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
            template.update("delete from comment where commentPostId =?", postId);
            template.update("delete from post where postId=?", postId);
            template.update("update community set communitySize=communitySize-1 where communityId=?", postComId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //查
    public Post selectPostById(int postId) {
        try {
            List<Post> posts = template.query("select * from post,user,community where postOwnerId=userId and postComId=communityId and postId=? ", postRowMapper, postId);
            return posts.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //多查询
    public List<Post> selectPosts(String param, int ownerId, String value, String order_by, int order, int pageSize, int page) {
        try {
            String sql = "select * from post,user,community where communityId=postComId and userId=postOwnerId ";
            if (ownerId != -1)
                sql += " and userId= " + ownerId;
            if (param != "all" || value != "all")
                sql += " and " + param + " like '%" + value + "%'";
            sql += " order by " + order_by;
            if (order == 1)
                sql += " desc";
            if (page != 0 || pageSize != 0)
                sql += " limit " + (page - 1) * pageSize + "," + pageSize;
            List<Post> posts = template.query(sql, postRowMapper);
            return posts;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
