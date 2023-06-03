package com.selkieanna.socialmediaapi.posting.repository;

import com.selkieanna.socialmediaapi.posting.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);
    boolean existsById(Long id);
}
