package com.example.feedserver.feed.service;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.dto.SocialFeedInfo;
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

    public List<SocialFeedInfo> getAllFeeds() {
        return feedRepository.findAllFeedsInfo();
    }

    public List<SocialFeedInfo> getAllFeedsByUploaderId(int uploaderId) {
        return feedRepository.findFeedsInfoByUploaderId(uploaderId);
    }

    public SocialFeedInfo getFeedById(int feedId) {
        SocialFeed socialFeed = feedRepository.findById(feedId).orElse(null);
        return convertToSocialFeedInfo(socialFeed);
    }

    public void deleteFeed(int feedId) {
        feedRepository.deleteById(feedId);
    }

    @Transactional
    public SocialFeedInfo createFeed(CreateFeedRequest feedRequest) {
        SocialFeed feed = convertToSocialFeed(feedRequest);
        return convertToSocialFeedInfo(feed);
    }

    private SocialFeed convertToSocialFeed(CreateFeedRequest feedRequest) {
        return SocialFeed.builder()
                .imageId(feedRequest.getImageId())
                .uploaderId(feedRequest.getUploaderId())
                .contents(feedRequest.getContents())
                .build();
    }

    private SocialFeedInfo convertToSocialFeedInfo(SocialFeed socialFeed) {
        return SocialFeedInfo.builder()
                .feedId(socialFeed.getFeedId())
                .imageId(socialFeed.getImageId())
                .uploaderId(socialFeed.getUploaderId())
                .uploadDatetime(socialFeed.getUploadDatetime())
                .contents(socialFeed.getContents())
                .build();
    }
}
