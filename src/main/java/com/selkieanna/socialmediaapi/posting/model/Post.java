package com.selkieanna.socialmediaapi.posting.model;

import com.selkieanna.socialmediaapi.authorization.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String header;
    @NotBlank
    private String text;
    @OneToMany(mappedBy = "post")
    private Set<Attachment> attachments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @CreationTimestamp
    private Date createdAt;

    public Post() {
    }

    public Post(String header, String text, Set<Attachment> attachments, User author) {
        this.header = header;
        this.text = text;
        this.attachments = attachments;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public User getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
