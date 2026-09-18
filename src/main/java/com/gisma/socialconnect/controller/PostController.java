package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.PostRequest;
import com.gisma.socialconnect.dto.PostResponse;
import com.gisma.socialconnect.dto.PostUpdateRequest;
import com.gisma.socialconnect.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts", description = "Short text updates users post, tagged by topic")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAll(@RequestParam(required = false) Integer topicId,
                                      @RequestParam(required = false) Integer authorId) {
        if (topicId != null) {
            return postService.getByTopic(topicId);
        }
        if (authorId != null) {
            return postService.getByAuthor(authorId);
        }
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody PostRequest request) {
        return postService.create(request);
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request) {
        return postService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
