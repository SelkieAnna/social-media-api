package com.selkieanna.socialmediaapi.posting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
}
