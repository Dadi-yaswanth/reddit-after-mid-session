package com.reddit.repository;

import com.reddit.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;


@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    Community findByCommunityName(String communityName);
    boolean existsByCommunityName(String communityName);
    @Query("SELECT c FROM Community c WHERE c.communityName ILIKE %:word%")
    Page<Community> findCommunitiesBySearchName(@Param("word") String keyword, Pageable pageable);
}