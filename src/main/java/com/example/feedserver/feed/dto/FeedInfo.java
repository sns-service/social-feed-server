package com.example.feedserver.feed.dto;

import com.example.feedserver.feed.entity.SocialFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class FeedInfo {

    private int feedId;
    private String imageId;
    private int uploaderId;
    private String uploaderName;
    private ZonedDateTime uploadDatetime;
    private String contents;

    public FeedInfo(SocialFeed feed, String uploaderName) {
        this.feedId = feed.getFeedId();
        this.imageId = feed.getImageId();
        this.uploaderId = feed.getUploaderId();
        this.uploaderName = uploaderName;
        this.uploadDatetime = feed.getUploadDatetime();
        this.contents = feed.getContents();
    }
}
