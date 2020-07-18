package com.Service;

import com.Bean.Community;
import com.Dao.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    //增
    public boolean addNewCommunity(Community newCommunity) {
        return communityRepository.insertCommunity(newCommunity);
    }

    public boolean collectCommunityUser(int userId, int communityId) {
        return communityRepository.insertCommunityUser(userId, communityId);
    }

    public boolean unCollectCommunityUser(int userId, int communityId) {
        return communityRepository.deleteCommunityUser(userId, communityId);
    }

    //删
    public boolean removeCommunity(int communityId) {
        return communityRepository.deleteCommunity(communityId);
    }

    //改
    public boolean modifyCommunity(Community modifyCommunity) {
        return communityRepository.updateCommunity(modifyCommunity);
    }

    //查
    public Community getCommunityById(int communityId) {
        return communityRepository.selectCommunityById(communityId);
    }

    public List<Community> getCommunities(String param, int ownerId, String value, String order_by, int order, int pageSize, int page) {
        return communityRepository.selectCommunities(param, ownerId, value, order_by, order, pageSize, page);
    }

    public List<Community> getCollectCommunities(int collectorId) {
        return communityRepository.selectCollectCommunities(collectorId);
    }
}
