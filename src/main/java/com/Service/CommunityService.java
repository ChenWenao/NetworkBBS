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


}
