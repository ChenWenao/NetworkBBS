package com.Dao;

import com.Bean.Community;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityRowMapper implements RowMapper<Community> {
    @Override
    public Community mapRow(ResultSet resultSet,int rowNum)throws SQLException{
        Community community =new Community();
        community.setCommunityId(resultSet.getInt("communityId"));
        community.setCommunityName(resultSet.getString("communityName"));
        community.setCommunityIcon(resultSet.getString("communityIcon"));
        community.setCommunityIntroduction(resultSet.getString("communityIntroduction"));
        community.setCommunityBuildTime(resultSet.getTime("communityBuildTime"));
        community.setCommunityHeat(resultSet.getInt("communityHeat"));
        community.setCommunitySize(resultSet.getInt("communitySize"));
        community.setCommunityOwnerId(resultSet.getInt("communityOwnerId"));
        return community;
    }
}
