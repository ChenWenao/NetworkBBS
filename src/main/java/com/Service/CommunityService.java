package com.Service;

import com.Bean.Community;
import com.Dao.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    //增
    public boolean addNewCommunity(Community newCommunity){
        return communityRepository.insertCommunity(newCommunity);
    }

    //删
    public boolean removeCommunity(int communityId){
        return communityRepository.deleteCommunity(communityId);
    }

    //改
    public boolean modifyCommunity(Community modifyCommunity){
        return communityRepository.updateCommunity(modifyCommunity);
    }

    //查
    public Community getCommunityById(int communityId){
        return communityRepository.selectCommunityById(communityId);
    }

}
