package com.example.feedserver.feed.controller;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.entity.SocialFeed;
import com.example.feedserver.feed.service.SocialFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class SocialFeedController {

    private final SocialFeedService feedService;

    @GetMapping
    public List<SocialFeed> getAllFeeds() {
        return feedService.getAllFeeds();
    }

    @GetMapping("/user/{userId}")
    public List<SocialFeed> getAllFeedsByUser(@PathVariable("userId") int userId) {
        return feedService.getAllFeedsByUploaderId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialFeed> getFeedById(@PathVariable("id") int id) {
        SocialFeed result = feedService.getFeedById(id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("id") int id) {
        feedService.deleteFeed(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public SocialFeed createFeed(@RequestBody CreateFeedRequest feedRequest) {
        return feedService.createFeed(feedRequest);
    }
}
