package com.example.feedserver.feed.repository;

import com.example.feedserver.feed.entity.SocialFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialFeedRepository extends JpaRepository<SocialFeed, Integer> {

    List<SocialFeed> findByUploaderId(int uploaderId);
}
