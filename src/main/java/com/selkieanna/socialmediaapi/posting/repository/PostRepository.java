package com.selkieanna.socialmediaapi.posting.repository;

import com.selkieanna.socialmediaapi.posting.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(FILTER_POSTS_ON_FOLLOW_FOLLOWER_ID_QUERY)
    Page<Post> findAllByFollowFollowerId(Long id, Pageable pageable);
    Optional<Post> findById(Long id);
    boolean existsById(Long id);

    String FILTER_POSTS_ON_FOLLOW_FOLLOWER_ID_QUERY = """
            select p 
            from Post p 
            join Follow f 
            on p.author.id=f.followee.id 
            where f.follower.id=?1""";
}
