package com.Dao;

import com.Bean.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                            "communityName," +
                            "communityIcon," +
                            "communityIntroduction," +
                            "communityOwnerId) values (?,?,?,?)",
                    newCommunity.getCommunityName(),
                    newCommunity.getCommunityIcon(),
                    newCommunity.getCommunityIntroduction(),
                    newCommunity.getCommunityOwnerId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //删
    public boolean deleteCommunity(int communityId) {
        try {
            template.update("delete from Community where communityId = ?", communityId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //改
    public boolean updateCommunity(Community modifyCommunity) {
        try {
            template.update("update Community set communityName=?," +
                            "communityIcon=?," +
                            "communityIntroduction=?" +
                            "where communityId=?",
                    modifyCommunity.getCommunityName(),
                    modifyCommunity.getCommunityIcon(),
                    modifyCommunity.getCommunityIntroduction(),
                    modifyCommunity.getCommunityId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    //查
    //通过id查询吧
    public Community selectCommunityById(int communityId) {
        try {
            List<Community> communities = template.query("select * from Community where communityId=?", communityRowMapper, communityId);
            return communities.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
