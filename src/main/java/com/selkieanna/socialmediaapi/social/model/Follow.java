package com.selkieanna.socialmediaapi.social.model;

import com.selkieanna.socialmediaapi.authorization.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User follower;
    @OneToOne
    private User followee;

    public Follow() {
    }

    public Follow(Long id, User follower, User followee) {
        this.id = id;
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
