package com.selkieanna.socialmediaapi.posting.response;

import java.util.List;

public class PostResponse {
    public Long postId;
    public String header;
    public String text;
    public List<Long> attachmentIds;
    public Long authorId;

    public PostResponse(Long postId, String header, String text, List<Long> attachmentIds, Long authorId) {
        this.postId = postId;
        this.header = header;
        this.text = text;
        this.attachmentIds = attachmentIds;
        this.authorId = authorId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
