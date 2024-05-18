package com.example.feedserver.feed.repository;

import com.example.feedserver.feed.dto.SocialFeedInfo;
import com.example.feedserver.feed.entity.SocialFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialFeedRepository extends JpaRepository<SocialFeed, Integer> {

    @Query("SELECT new com.example.feedserver.feed.dto.SocialFeedInfo(f.feedId, f.imageId, f.uploaderId, f.uploadDatetime, f.contents) FROM SocialFeed f")
    List<SocialFeedInfo> findAllFeedsInfo();

    @Query("SELECT new com.example.feedserver.feed.dto.SocialFeedInfo(f.feedId, f.imageId, f.uploaderId, f.uploadDatetime, f.contents) FROM SocialFeed f WHERE f.uploaderId = :uploaderId")
    List<SocialFeedInfo> findFeedsInfoByUploaderId(@Param("uploaderId") int uploaderId);
}
