package com.example.feedserver.feed.service;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.repository.SocialFeedRepository;
import com.example.feedserver.feed.entity.SocialFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialFeedService {

    private final SocialFeedRepository feedRepository;

    public List<SocialFeed> getAllFeeds() {
        return feedRepository.findAll();
    }

    public List<SocialFeed> getAllFeedsByUploaderId(int uploaderId) {
        return feedRepository.findByUploaderId(uploaderId);
    }

    public SocialFeed getFeedById(int feedId) {
        return feedRepository.findById(feedId).orElse(null);
    }

    public void deleteFeed(int feedId) {
        feedRepository.deleteById(feedId);
    }

    @Transactional
    public SocialFeed createFeed(CreateFeedRequest feedRequest) {
        SocialFeed feed = convertToSocialFeed(feedRequest);
        return feedRepository.save(feed);
    }

    private SocialFeed convertToSocialFeed(CreateFeedRequest feedRequest) {
        return SocialFeed.builder()
                .imageId(feedRequest.getImageId())
                .uploaderId(feedRequest.getUploaderId())
                .contents(feedRequest.getContents())
                .build();
    }
}
