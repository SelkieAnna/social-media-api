package com.selkieanna.socialmediaapi.posting.controller;

import com.selkieanna.socialmediaapi.authorization.model.User;
import com.selkieanna.socialmediaapi.authorization.repository.UserRepository;
import com.selkieanna.socialmediaapi.posting.model.Attachment;
import com.selkieanna.socialmediaapi.posting.model.Post;
import com.selkieanna.socialmediaapi.posting.repository.AttachmentRepository;
import com.selkieanna.socialmediaapi.posting.repository.PostRepository;
import com.selkieanna.socialmediaapi.posting.request.PostingRequest;
import com.selkieanna.socialmediaapi.posting.response.AttachmentResponse;
import com.selkieanna.socialmediaapi.posting.response.PostResponse;
import com.selkieanna.socialmediaapi.posting.service.PostingValidationService;
import com.selkieanna.socialmediaapi.util.response.MessageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostingController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostingValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<?> newPost(@RequestBody PostingRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found"));
        Post post = new Post(request.getHeader(), request.getText(), new HashSet<Attachment>(), user);
        postRepository.save(post);
        return ResponseEntity.ok(new PostResponse(post.getId(), post.getHeader(), post.getText(),
                post.getAttachments().stream().map(Attachment::getId).toList(), post.getAuthor().getId()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> viewPost(@PathVariable String postId) {
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Post not found"));
        }
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Error: Post not found"));
        List<Long> attachmentIds = post.getAttachments().stream()
                .map(Attachment::getId).collect(Collectors.toList());
        return ResponseEntity.ok(new PostResponse(Long.valueOf(postId), post.getHeader(), post.getText(),
                attachmentIds, post.getAuthor().getId()));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> editPost(@PathVariable String postId, @RequestBody PostingRequest request) {
        // check if the post exists
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please specify valid post"));
        }
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Error: Post not found"));

        if (!validationService.isPostAuthor(post, SecurityContextHolder.getContext())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not your post"));
        }

        post.setHeader(request.getHeader());
        post.setText(request.getText());
        postRepository.save(post);
        return ResponseEntity.ok(new PostResponse(Long.valueOf(postId), post.getHeader(), post.getText(),
                post.getAttachments().stream().map(Attachment::getId).toList(), post.getAuthor().getId()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId) {
        // check if the post exists
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please specify valid post"));
        }
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Error: Post not found"));

        if (!validationService.isPostAuthor(post, SecurityContextHolder.getContext())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not your post"));
        }

        // delete all attachments
        post.getAttachments().forEach(attachment -> attachmentRepository.delete(attachment));

        postRepository.delete(post);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @PostMapping(value = "/{postId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAttachment(@PathVariable String postId, @RequestParam("file") MultipartFile file) {
        // check if the post exists
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please specify valid post"));
        }
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Error: Post not found"));

        if (!validationService.isPostAuthor(post, SecurityContextHolder.getContext())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not your post"));
        }

        try {
            Attachment attachment = new Attachment(new SerialBlob(file.getBytes()), post);
            attachmentRepository.save(attachment);
            return ResponseEntity.ok(new AttachmentResponse(attachment.getId(), attachment.getPost().getId()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: File could not be read"));
        } catch (SerialException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: File could not be serialized"));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: File could not be saved to bytes"));
        }
    }

    @GetMapping("/{postId}/attachments/{attachmentId}")
    @Transactional
    public ResponseEntity<?> viewAttachment(@PathVariable String postId, @PathVariable Long attachmentId) {
        // check if the post exists
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Post does not exist"));
        }
        // check if attachment exists
        if (!attachmentRepository.existsById(attachmentId)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Attachment does not exist"));
        }

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Error: Attachment not found"));
        Blob attachmentData = attachment.getData();
        try {
            byte[] attachmentDataBytes = attachmentData.getBytes(1, (int) attachmentData.length());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${attachmentId}\"")
                    .body(attachmentDataBytes);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: File bytes not found"));
        }
    }

    @DeleteMapping("/{postId}/attachments/{attachmentId}")
    public ResponseEntity<?> deleteAttachment(@PathVariable String postId, @PathVariable String attachmentId) {
        // check if the post exists
        if (!postRepository.existsById(Long.valueOf(postId))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please specify valid post"));
        }
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Error: Post not found"));

        if (!validationService.isPostAuthor(post, SecurityContextHolder.getContext())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not your post"));
        }

        attachmentRepository.deleteById(Long.valueOf(attachmentId));
        return ResponseEntity.ok(new MessageResponse("Success"));
    }
}
