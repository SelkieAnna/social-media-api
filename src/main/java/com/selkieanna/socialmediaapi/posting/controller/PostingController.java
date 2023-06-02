package com.selkieanna.socialmediaapi.posting.controller;

import com.selkieanna.socialmediaapi.posting.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostingController {
    @Autowired
    PostRepository postRepository;

}
