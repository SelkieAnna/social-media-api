package com.selkieanna.socialmediaapi.posting.model;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private Blob data;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Attachment() {
    }

    public Attachment(Blob data, Post post) {
        this.data = data;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public Blob getData() {
        return data;
    }

    public Post getPost() {
        return post;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
