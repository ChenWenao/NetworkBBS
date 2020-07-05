package com.Dao;

import com.Bean.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityRepository {
    @Autowired
    private JdbcTemplate template;
    private CommunityRowMapper communityRowMapper = new CommunityRowMapper();

    //增
    //新建吧
    public boolean insertCommunity(Community newCommunity) {
        try {
            template.update("insert into networkbbs.community(" +
                    "communityName" +
                    "communityIcon" +
                    "communityIntroduction" +
                    "communityOwnerId,) values (?,?,?,?)",
                    newCommunity.getCommunityName(),
                    newCommunity.getCommunityIcon(),
                    newCommunity.getCommunityIntroduction(),
                    newCommunity.getCommunityOwnerId());
            return true;
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return false;
        }
    }


}
