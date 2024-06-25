package com.stonks.repository;

import com.stonks.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {
    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
}
