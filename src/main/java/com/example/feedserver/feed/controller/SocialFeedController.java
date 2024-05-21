package com.example.feedserver.feed.controller;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.dto.FeedInfo;
import com.example.feedserver.feed.dto.FeedResponse;
import com.example.feedserver.feed.entity.SocialFeed;
import com.example.feedserver.feed.service.SocialFeedService;
import com.example.feedserver.userinfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class SocialFeedController {

    private final SocialFeedService feedService;

    @GetMapping
    public List<FeedInfo> getAllFeeds() {
        List<SocialFeed> allFeeds = feedService.getAllFeeds();

        // TODO : 성능 개선 & 리팩토링
        List<FeedInfo> result = new ArrayList<>();
        for (SocialFeed feed : allFeeds) {
            UserInfo user = feedService.getUserInfo(feed.getUploaderId());
            FeedInfo feedInfo = new FeedInfo(feed, user.getUsername());
            result.add(feedInfo);
        }

        return result;
    }

    @GetMapping("/user/{userId}")
    public List<FeedResponse> getAllFeedsByUser(@PathVariable("userId") int userId) {
        return feedService.getAllFeedsByUploaderId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedResponse> getFeedById(@PathVariable("id") int id) {
        FeedResponse result = feedService.getFeedById(id);

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
    public FeedResponse createFeed(@RequestBody CreateFeedRequest feedRequest) {
        return feedService.createFeed(feedRequest);
    }
}
