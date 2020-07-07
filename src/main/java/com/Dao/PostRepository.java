package com.Dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {
    @Autowired
    private JdbcTemplate template;
    private PostRowMapper postRowMapper=new PostRowMapper();

    //增加
    public boolean insertPost(String postTitle,String postContent,int postOwnerId,int postComId){
        try {
            template.update("insert into post(postTitle," +
                    "postContent," +
                    "postOwnerId," +
                    "postComId) values (?,?,?,?)",postTitle,postContent,postOwnerId,postComId);
            return true;

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }


}
