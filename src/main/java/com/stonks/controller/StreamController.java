package com.stonks.controller;

import com.stonks.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/start")
    public ResponseEntity<?> startStream(@RequestBody StreamRequest streamRequest) {
        // Notify followers via WebSocket or email
        notificationService.sendNotification("Stream started", "followerToken");
        notificationService.sendEmail("Stream started", "followerEmail");
        return ResponseEntity.ok("Stream started successfully");
    }
}
