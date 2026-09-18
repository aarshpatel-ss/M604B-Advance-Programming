package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.CommentRequest;
import com.gisma.socialconnect.dto.CommentResponse;
import com.gisma.socialconnect.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Comments", description = "Replies to a post")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    public List<CommentResponse> getByPost(@PathVariable Long postId) {
        return commentService.getByPost(postId);
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@PathVariable Long postId, @Valid @RequestBody CommentRequest request) {
        return commentService.create(postId, request);
    }

    @DeleteMapping("/api/v1/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
