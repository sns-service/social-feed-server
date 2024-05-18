package com.example.feedserver.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class SocialFeedInfo {

    private int feedId;
    private String imageId;
    private int uploaderId;
    private ZonedDateTime uploadDatetime;
    private String contents;
}
