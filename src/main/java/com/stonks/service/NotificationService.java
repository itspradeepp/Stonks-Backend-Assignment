package com.stonks.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String message, String token) {
        // Implement logic to send push notification
        Message msg = Message.builder()
                .putData("message", message)
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(msg);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String message, String email) {
        // Implement logic to send email notification
        // This can be implemented using JavaMailSender
    }
}
