package com.selkieanna.socialmediaapi.social.controller;

import com.selkieanna.socialmediaapi.authorization.model.User;
import com.selkieanna.socialmediaapi.authorization.repository.UserRepository;
import com.selkieanna.socialmediaapi.social.model.Follow;
import com.selkieanna.socialmediaapi.social.repository.FollowRepository;
import com.selkieanna.socialmediaapi.util.response.BooleanResponse;
import com.selkieanna.socialmediaapi.util.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> followUser(@PathVariable("userId") String followeeId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User follower = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Current user not found"));
        User followee = userRepository.findById(Long.valueOf(followeeId))
                .orElseThrow(() -> new RuntimeException("Error: Followed user not found"));
        // check if already followed
        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
        Follow follow = new Follow(follower, followee);
        followRepository.save(follow);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @DeleteMapping("/follow/{userId}")
    public ResponseEntity<?> unfollowUser(@PathVariable("userId") String followeeId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User follower = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Current user not found"));
        User followee = userRepository.findById(Long.valueOf(followeeId))
                .orElseThrow(() -> new RuntimeException("Error: Followed user not found"));
        // check if already unfollowed
        if (!followRepository.existsByFollowerAndFollowee(follower, followee)) {
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new RuntimeException("Error: Follow not found"));
        followRepository.delete(follow);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @GetMapping("/isfriend/{userId}")
    public ResponseEntity<?> isFriend(@PathVariable("userId") String friendId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User follower = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Current user not found"));
        User followee = userRepository.findById(Long.valueOf(friendId))
                .orElseThrow(() -> new RuntimeException("Error: Followed user not found"));
        if (followRepository.existsByFollowerAndFollowee(follower, followee) &&
            followRepository.existsByFollowerAndFollowee(followee, follower)) {
            return ResponseEntity.ok(new BooleanResponse(true));
        }
        return ResponseEntity.ok(new BooleanResponse(false));
    }


}
