package com.selkieanna.socialmediaapi.posting.service;

import com.selkieanna.socialmediaapi.authorization.model.User;
import com.selkieanna.socialmediaapi.authorization.repository.UserRepository;
import com.selkieanna.socialmediaapi.posting.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PostingValidationService {
    @Autowired
    UserRepository userRepository;

    public boolean isPostAuthor(Post post, SecurityContext securityContext) {
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User user = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found"));
        // check if user authored the post
        return post.getId().equals(user.getId());
    }
}
