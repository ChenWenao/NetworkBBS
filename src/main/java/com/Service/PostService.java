package com.Service;

import com.Bean.Post;
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

    //删
    public boolean removePost(int postId,int postComId){
        return postRepository.deletePost(postId, postComId);
    }



    //查
    public Post getPostById(int postId){
        return postRepository.selectPostById(postId);
    }



}
