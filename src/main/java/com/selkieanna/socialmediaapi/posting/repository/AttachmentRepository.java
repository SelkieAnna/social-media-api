package com.selkieanna.socialmediaapi.posting.repository;

import com.selkieanna.socialmediaapi.posting.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}
