package com.selkieanna.socialmediaapi.social.model;

import com.selkieanna.socialmediaapi.authorization.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User follower;
    @ManyToOne
    private User followee;

    public Follow() {
    }

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public Long getId() {
        return id;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }


}
