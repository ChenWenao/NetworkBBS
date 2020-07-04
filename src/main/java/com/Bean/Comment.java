package com.Bean;

import java.util.Date;

public class Comment {
    private int commentId;
    private String commentContent;
    private int commentPostId;
    private int commentOwnerId;
    private String commentReplyName;
    private String commentReplyContent;
    private Date commentTime;
    private boolean commentRead;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentPostId() {
        return commentPostId;
    }

    public void setCommentPostId(int commentPostId) {
        this.commentPostId = commentPostId;
    }

    public int getCommentOwnerId() {
        return commentOwnerId;
    }

    public void setCommentOwnerId(int commentOwnerId) {
        this.commentOwnerId = commentOwnerId;
    }

    public String getCommentReplyName() {
        return commentReplyName;
    }

    public void setCommentReplyName(String commentReplyName) {
        this.commentReplyName = commentReplyName;
    }

    public String getCommentReplyContent() {
        return commentReplyContent;
    }

    public void setCommentReplyContent(String commentReplyContent) {
        this.commentReplyContent = commentReplyContent;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public boolean isCommentRead() {
        return commentRead;
    }

    public void setCommentRead(boolean commentRead) {
        this.commentRead = commentRead;
    }

}
