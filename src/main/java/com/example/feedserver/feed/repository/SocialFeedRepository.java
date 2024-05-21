package com.example.feedserver.feed.repository;

import com.example.feedserver.feed.dto.FeedResponse;
import com.example.feedserver.feed.entity.SocialFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialFeedRepository extends JpaRepository<SocialFeed, Integer> {

    @Query("SELECT f FROM SocialFeed f")
    List<SocialFeed> findAllFeeds();



    @Query("SELECT new com.example.feedserver.feed.dto.FeedResponse(f.feedId, f.imageId, f.uploaderId, f.uploadDatetime, f.contents) FROM SocialFeed f WHERE f.uploaderId = :uploaderId")
    List<FeedResponse> findFeedsInfoByUploaderId(@Param("uploaderId") int uploaderId);
}
