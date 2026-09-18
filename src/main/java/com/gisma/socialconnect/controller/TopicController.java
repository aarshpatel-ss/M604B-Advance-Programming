package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.TopicRequest;
import com.gisma.socialconnect.dto.TopicResponse;
import com.gisma.socialconnect.service.TopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
@Tag(name = "Topics", description = "CRUD operations on discussion topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<TopicResponse> getAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return topicService.search(search);
        }
        return topicService.getAll();
    }

    @GetMapping("/{id}")
    public TopicResponse getById(@PathVariable Integer id) {
        return topicService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponse create(@Valid @RequestBody TopicRequest request) {
        return topicService.create(request);
    }

    @PutMapping("/{id}")
    public TopicResponse update(@PathVariable Integer id, @Valid @RequestBody TopicRequest request) {
        return topicService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
