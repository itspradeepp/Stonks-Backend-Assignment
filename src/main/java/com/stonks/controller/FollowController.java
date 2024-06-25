package com.stonks.controller;

import com.stonks.exception.ResourceNotFoundException;
import com.stonks.model.Follow;
import com.stonks.model.Profile;
import com.stonks.repository.FollowRepository;
import com.stonks.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<?> followUser(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        Profile follower = profileRepository.findById(followerId)
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found"));
        Profile following = profileRepository.findById(followingId)
                .orElseThrow(() -> new ResourceNotFoundException("Following not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);

        return ResponseEntity.ok("Followed successfully");
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<?> unfollowUser(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        return ResponseEntity.ok("Unfollowed successfully");
    }
}
