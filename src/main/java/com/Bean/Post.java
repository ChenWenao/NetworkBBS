package com.Bean;

import java.util.Date;

public class Post {
    private int postId;
    private String postTitle;
    private String postContent;
    private Date postTime;
    private int postHeat;
    private int postReplies;
    private int postOwnerId;
    private String postOwnerName;
    private int postComId;
    private String postComName;

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }


    public String getPostComName() {
        return postComName;
    }

    public void setPostComName(String postComName) {
        this.postComName = postComName;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public int getPostHeat() {
        return postHeat;
    }

    public void setPostHeat(int postHeat) {
        this.postHeat = postHeat;
    }

    public int getPostReplies() {
        return postReplies;
    }

    public void setPostReplies(int postReplies) {
        this.postReplies = postReplies;
    }

    public int getPostOwnerId() {
        return postOwnerId;
    }

    public void setPostOwnerId(int postOwnerId) {
        this.postOwnerId = postOwnerId;
    }

    public int getPostComId() {
        return postComId;
    }

    public void setPostComId(int postComId) {
        this.postComId = postComId;
    }

}
