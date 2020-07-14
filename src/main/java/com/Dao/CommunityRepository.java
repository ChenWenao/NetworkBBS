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
    public boolean insertCommunityUser(int userId, int communityId) {
        try {
            template.update("insert into user_community values (?,?)", userId, communityId);
            template.update("update community set communityHeat=communityHeat+1 where communityId=?", communityId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    //取关吧
    public boolean deleteCommunityUser(int userId, int communityId) {
        try {
            template.update("delete from user_community where userId=? and communityId=?", userId, communityId);
            template.update("update community set communityHeat=communityHeat-1 where communityId=?", communityId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    //删
    public boolean deleteCommunity(int communityId) {
        try {
            template.update("delete from comment where commentPostId in (select postId from post,comment where postComId=commentId and commentId=?)", communityId);
            template.update("delete from post where postComId = ?", communityId);
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
    public Community selectCommunityById(int communityId) {
        try {
            List<Community> communities = template.query("select * from community,user where communityOwnerId=userId and communityId=?", communityRowMapper, communityId);
            return communities.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //多查询
    //param：用于搜索，表示搜索哪个字段
    //qw：用于搜索，搜索param的字段中包括value的结果。
    //若param和value都为“all”，表示不定向搜索。
    //order_by：表示根据哪个字段排序。默认为 communityHeat
    //order：用于排序，为0表示正序，为1表示倒序。 默认为 正序
    //pageSize：表示分页页面大小。 默认为 5
    //page：表示查询第几页的数据。 默认为 1
    //若pageSize和page都为0，则不分页，返回所有数据。
    public List<Community> selectCommunities(String param, int ownerId, String value, String order_by, int order, int pageSize, int page) {
        try {
            String sql = "select * from community,user where userId = communityOwnerId ";
            if (ownerId != -1)
                sql += " and userId = " + ownerId;
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

    public List<Community> selectCollectCommunities(int collertorId) {
        try {
            List<Community> communities = template.query("select * from Community,user,user_community" +
                    " where user.userId=user_community.userId " +
                    " and community.communityId=user_community.communityId" +
                    " and userId=?", communityRowMapper, collertorId);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
