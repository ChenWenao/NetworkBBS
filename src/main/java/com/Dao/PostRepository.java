package com.Dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private PostRowMapper postRowMapper=new PostRowMapper();




}
