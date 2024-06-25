package com.stonks.model;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Profile follower;

    @ManyToOne
    private Profile following;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Profile getFollowing() {
        return following;
    }

    public void setFollowing(Profile following) {
        this.following = following;
    }

}
