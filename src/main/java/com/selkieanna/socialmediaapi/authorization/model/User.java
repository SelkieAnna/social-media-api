package com.selkieanna.socialmediaapi.authorization.model;

import com.selkieanna.socialmediaapi.posting.model.Post;
import com.selkieanna.socialmediaapi.social.model.Follow;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "author")
    private Set<Post> posts;
    @OneToMany(mappedBy = "follower")
    private Set<Follow> follows;
    @OneToMany(mappedBy = "followee")
    private Set<Follow> followedBy;

    public User() {
    }

    public User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles(){
        return roles;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Set<Follow> getFollows() {
        return follows;
    }

    public Set<Follow> getFollowedBy() {
        return followedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void setFollows(Set<Follow> follows) {
        this.follows = follows;
    }

    public void setFollowedBy(Set<Follow> followedBy) {
        this.followedBy = followedBy;
    }
}
