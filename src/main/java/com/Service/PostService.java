package com.Service;

import com.Dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    //增
    public boolean addNewPost(String postTitle,String postContent,int postOwnerId,int postComId){
        return postRepository.insertPost(postTitle, postContent, postOwnerId, postComId);
    }



}
