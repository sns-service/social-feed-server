package com.example.feedserver.feed.controller;

import com.example.feedserver.feed.dto.CreateFeedRequest;
import com.example.feedserver.feed.dto.SocialFeedInfo;
import com.example.feedserver.feed.service.SocialFeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SocialFeedControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SocialFeedService feedService;

    @InjectMocks
    private SocialFeedController feedController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(feedController).build();
    }

    @Test
    @DisplayName("피드 전체 조회 테스트")
    void testGetAllFeeds() throws Exception {
        SocialFeedInfo feedInfo1 = new SocialFeedInfo(1, "img1", 1, ZonedDateTime.now(), "Content 1");
        SocialFeedInfo feedInfo2 = new SocialFeedInfo(2, "img2", 2, ZonedDateTime.now(), "Content 2");

        when(feedService.getAllFeeds()).thenReturn(Arrays.asList(feedInfo1, feedInfo2));

        mockMvc.perform(get("/api/feeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].feedId").value(feedInfo1.getFeedId()))
                .andExpect(jsonPath("$[1].feedId").value(feedInfo2.getFeedId()));

        verify(feedService, times(1)).getAllFeeds();
    }

    @Test
    @DisplayName("유저의 피드 조회")
    void testGetAllFeedsByUser() throws Exception {
        SocialFeedInfo feedInfo = new SocialFeedInfo(1, "img1", 1, ZonedDateTime.now(), "Content 1");

        when(feedService.getAllFeedsByUploaderId(1)).thenReturn(Arrays.asList(feedInfo));

        mockMvc.perform(get("/api/feeds/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].feedId").value(feedInfo.getFeedId()));

        verify(feedService, times(1)).getAllFeedsByUploaderId(1);
    }

    @Test
    void testGetFeedById() throws Exception {
        SocialFeedInfo feedInfo = new SocialFeedInfo(1, "img1", 1, ZonedDateTime.now(), "Content 1");

        when(feedService.getFeedById(1)).thenReturn(feedInfo);

        mockMvc.perform(get("/api/feeds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedId").value(feedInfo.getFeedId()));

        verify(feedService, times(1)).getFeedById(1);
    }

    @Test
    void testDeleteFeed() throws Exception {
        doNothing().when(feedService).deleteFeed(1);

        mockMvc.perform(delete("/api/feeds/1"))
                .andExpect(status().isOk());

        verify(feedService, times(1)).deleteFeed(1);
    }

    @Test
    void testCreateFeed() throws Exception {
        CreateFeedRequest request = new CreateFeedRequest("img1", 1, "Content 1");
        SocialFeedInfo feedInfo = new SocialFeedInfo(1, "img1", 1, ZonedDateTime.now(), "Content 1");

        when(feedService.createFeed(any(CreateFeedRequest.class))).thenReturn(feedInfo);

        mockMvc.perform(post("/api/feeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"imageId\":\"img1\", \"uploaderId\":1, \"contents\":\"Content 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedId").value(feedInfo.getFeedId()));

        verify(feedService, times(1)).createFeed(any(CreateFeedRequest.class));
    }
}