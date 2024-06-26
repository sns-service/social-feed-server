package com.example.feedserver.feed.service;

import com.example.feedserver.exception.BadRequestException;
import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.dto.FeedInfo;
import com.example.feedserver.feed.dto.FeedResponse;
import com.example.feedserver.feed.entity.SocialFeed;
import com.example.feedserver.feed.repository.SocialFeedRepository;
import com.example.feedserver.userinfo.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialFeedService {

    private final SocialFeedRepository feedRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${sns.user-server}")
    private String userServerUrl;

    private RestClient restClient = RestClient.create();

    public List<SocialFeed> getAllFeeds() {
        return feedRepository.findAllFeeds();
    }

    public List<FeedResponse> getAllFeedsByUploaderId(int uploaderId) {
        return feedRepository.findFeedsInfoByUploaderId(uploaderId);
    }

    public FeedResponse getFeedById(int feedId) {
        SocialFeed socialFeed = feedRepository.findById(feedId).orElse(null);
        return convertToSocialFeedResponse(socialFeed);
    }

    @Transactional
    public FeedResponse createFeed(CreateFeedRequest feedRequest, int userId) {
        if (feedRequest.getUploaderId() != userId) {
            throw new BadRequestException();
        }
        SocialFeed feed = feedRepository.save(convertToSocialFeed(feedRequest));

        UserInfo uploader = getUserInfo(feed.getUploaderId());
        FeedInfo feedInfo = new FeedInfo(feed, uploader.getUsername());

        sendCreateMessageToKafka(feedInfo);

        return convertToSocialFeedResponse(feed);
    }

    public void deleteFeed(int feedId, int userId) {
        SocialFeed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new BadRequestException());

        if (feed.getUploaderId() != userId) {
            throw new BadRequestException();
        }

        UserInfo userInfo = getUserInfo(userId);
        FeedInfo feedInfo = new FeedInfo(feed, userInfo.getUsername());

        feedRepository.deleteById(feedId);
        sendDeleteMessageToKafka(feedInfo);
    }

    private void sendCreateMessageToKafka(FeedInfo feedInfo) {
        try {
            kafkaTemplate.send("feed.created", objectMapper.writeValueAsString(feedInfo));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDeleteMessageToKafka(FeedInfo feedInfo) {
        try {
            kafkaTemplate.send("feed.deleted", objectMapper.writeValueAsString(feedInfo));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshAllFeeds() {
        List<SocialFeed> feeds = getAllFeeds();
        for (SocialFeed feed: feeds) {
            UserInfo uploader = getUserInfo(feed.getUploaderId());
            FeedInfo feedInfo = new FeedInfo(feed, uploader.getUsername());

            sendCreateMessageToKafka(feedInfo);
        }
    }

    public void deleteAllFeeds() {
        List<SocialFeed> feeds = getAllFeeds();
        for (SocialFeed feed: feeds) {
            UserInfo uploader = getUserInfo(feed.getUploaderId());
            FeedInfo feedInfo = new FeedInfo(feed, uploader.getUsername());

            sendDeleteMessageToKafka(feedInfo);
        }
    }

    public UserInfo getUserInfo(int userId) {
        return restClient.get()
                .uri(userServerUrl + "/api/users/" + userId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("invalid server response " + response.getStatusText());
                })
                .body(UserInfo.class);
    }

    private SocialFeed convertToSocialFeed(CreateFeedRequest feedRequest) {
        return SocialFeed.builder()
                .imageId(feedRequest.getImageId())
                .uploaderId(feedRequest.getUploaderId())
                .contents(feedRequest.getContents())
                .build();
    }

    private FeedResponse convertToSocialFeedResponse(SocialFeed socialFeed) {
        return FeedResponse.builder()
                .feedId(socialFeed.getFeedId())
                .imageId(socialFeed.getImageId())
                .uploaderId(socialFeed.getUploaderId())
                .uploadDatetime(socialFeed.getUploadDatetime())
                .contents(socialFeed.getContents())
                .build();
    }
}
