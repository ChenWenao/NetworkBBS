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
            template.update("insert into community(" +
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

    //关注吧
    public boolean insertCommunityUser(int userId,int communityId){
        try {
            template.update("insert into user_community values (?,?)",userId,communityId);
            template.update("update community set communityHeat=communityHeat+1");
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;

    }

    //取关吧
    public boolean deleteCommunityUser(int userId,int communityId){
        try {
            template.update("delete from user_community where userId=? and communityId=?",userId,communityId);
            template.update("update community set communityHeat=communityHeat-1");
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;

    }

    //删
    public boolean deleteCommunity(int communityId) {
        try {
            template.update("delete from community where communityId = ?", communityId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //改
    public boolean updateCommunity(Community modifyCommunity) {
        try {
            template.update("update community set communityName=?," +
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
    //单查询
    public Community selectCommunityById(int communityId){
        try {
            List<Community> communities=template.query("select * from community,user where communityId=?",communityRowMapper,communityId);
            return communities.get(0);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    //多查询
    public List<Community> selectCommunities(String param, String value, String order_by, int order, int pageSize, int page) {
        try {
            String sql = "select * from community,user where userId = communityOwnerId ";
            if (param != "all" || value != "all")
                sql += " and " + param + " like '%" + value + "%'";
            sql += " order by " + order_by;
            if (order == 1)
                sql += " desc";
            if (page != 0 || pageSize != 0)
                sql += " limit " + (page - 1) * pageSize + "," + pageSize;
            List<Community> communities = template.query(sql, communityRowMapper);
            return communities;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
