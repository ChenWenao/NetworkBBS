package com.Dao;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {
    @Autowired
    private JdbcTemplate template;
    private CommentRowMapper commentRowMapper=new CommentRowMapper();




}
