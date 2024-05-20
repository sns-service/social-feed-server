package com.example.feedserver.feed.service;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.dto.SocialFeedInfo;
import com.example.feedserver.feed.entity.SocialFeed;
import com.example.feedserver.feed.repository.SocialFeedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SocialFeedServiceTest {

    @Mock
    private SocialFeedRepository feedRepository;

    @InjectMocks
    private SocialFeedService feedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("피드 전체 조회 테스트")
    void getAllFeeds() {
        SocialFeed feed1 = new SocialFeed(1, "img1", 1, ZonedDateTime.now(), "content 1");
        SocialFeed feed2 = new SocialFeed(2, "img2", 2, ZonedDateTime.now(), "content 2");

        List<SocialFeedInfo> feedInfos = Arrays.asList(
                new SocialFeedInfo(feed1.getFeedId(), feed1.getImageId(), feed1.getUploaderId(), feed1.getUploadDatetime(), feed1.getContents()),
                new SocialFeedInfo(feed2.getFeedId(), feed2.getImageId(), feed2.getUploaderId(), feed2.getUploadDatetime(), feed2.getContents())
        );
        when(feedRepository.findAllFeedsInfo()).thenReturn(feedInfos);

        List<SocialFeedInfo> result = feedService.getAllFeeds();

        assertEquals(2, result.size());
        verify(feedRepository, times(1)).findAllFeedsInfo();
    }

    @Test
    @DisplayName("id로 피드 조회 테스트")
    void getFeedById() {
        SocialFeed feed = new SocialFeed(1, "img1", 1, ZonedDateTime.now(), "content 1");
        when(feedRepository.findById(1)).thenReturn(Optional.of(feed));

        SocialFeedInfo result = feedService.getFeedById(1);

        assertNotNull(result);
        assertEquals(feed.getFeedId(), result.getFeedId());
        verify(feedRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("id로 피드 삭제 테스트")
    void deleteFeed() {
        doNothing().when(feedRepository).deleteById(1);

        feedService.deleteFeed(1);

        verify(feedRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("피드 생성 테스트")
    void createFeed() {
        CreateFeedRequest request = new CreateFeedRequest("img1", 1, "Content 1");
        SocialFeed feed = new SocialFeed(1, "img1", 1, ZonedDateTime.now(), "Content 1");
        when(feedRepository.save(any(SocialFeed.class))).thenReturn(feed);

        SocialFeedInfo result = feedService.createFeed(request);

        assertNotNull(result);
        assertEquals(request.getImageId(), result.getImageId());
        verify(feedRepository, times(1)).save(any(SocialFeed.class));
    }
}