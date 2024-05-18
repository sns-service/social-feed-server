package com.example.feedserver.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFeedRequest {

    private String imageId;
    private int uploaderId;
    private String contents;
}
