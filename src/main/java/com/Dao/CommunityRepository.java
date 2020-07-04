package com.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityRepository {
    @Autowired
    private JdbcTemplate template;
    private CommunityRowMapper communityRowMapper=new CommunityRowMapper();





}
