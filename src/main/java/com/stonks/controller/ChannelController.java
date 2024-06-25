package com.stonks.controller;

import com.stonks.model.Channel;
import com.stonks.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        channel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        channelRepository.save(channel);
        return ResponseEntity.ok(channel);
    }
}
