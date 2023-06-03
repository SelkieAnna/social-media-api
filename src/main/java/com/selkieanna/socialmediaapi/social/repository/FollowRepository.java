package com.selkieanna.socialmediaapi.social.repository;

import com.selkieanna.socialmediaapi.authorization.model.User;
import com.selkieanna.socialmediaapi.social.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
    boolean existsByFollowerAndFollowee(User follower, User followee);
}
