package com.Service;

import com.Bean.Post;
import com.Dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Post> getPosts(String param,int ownerId,String value,String order_by,int order,int pageSize,int page){
        return postRepository.selectPosts(param, ownerId, value, order_by, order, pageSize, page);
    }


}
