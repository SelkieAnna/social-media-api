package com.selkieanna.socialmediaapi.posting.response;

public class AttachmentResponse {
    private Long attachmentId;
    private Long postId;

    public AttachmentResponse(Long attachmentId, Long postId) {
        this.attachmentId = attachmentId;
        this.postId = postId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
