package com.stonks.service;

import com.stonks.model.Message;
import com.stonks.model.Role;
import com.stonks.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {
    private Map<String, User> users = new HashMap<>();

    // Method to set admin
    public boolean setAdmin(Message message) {
        String recipientUsername = message.getRecipient();
        User recipient = users.get(recipientUsername);
        if (recipient == null) {
            recipient = new User();
            recipient.setUsername(recipientUsername);
            users.put(recipientUsername, recipient);
        }
        recipient.getRoles().add(Role.ADMIN);
        return true;
    }

    // Method to unset admin
    public boolean unsetAdmin(Message message) {
        String recipientUsername = message.getRecipient();
        User recipient = users.get(recipientUsername);
        if (recipient != null) {
            recipient.getRoles().remove(Role.ADMIN);
            return true;
        }
        return false;
    }

    // Method to mute a user
    public boolean muteUser(Message message) {
        String recipientUsername = message.getRecipient();
        User recipient = users.get(recipientUsername);
        if (recipient == null) {
            recipient = new User();
            recipient.setUsername(recipientUsername);
            users.put(recipientUsername, recipient);
        }
        recipient.setMuted(true);
        return true;
    }

    // Method to unmute a user
    public boolean unmuteUser(Message message) {
        String recipientUsername = message.getRecipient();
        User recipient = users.get(recipientUsername);
        if (recipient != null) {
            recipient.setMuted(false);
            return true;
        }
        return false;
    }

    // Method to ban a user
    public boolean banUser(Message message) {
        String recipientUsername = message.getRecipient();
        User recipient = users.get(recipientUsername);
        if (recipient != null) {
            // Implement banning logic (e.g., remove user from active users)
            users.remove(recipientUsername);
            return true;
        }
        return false;
    }

    // Method to unban a user
    public boolean unbanUser(Message message) {
        String recipientUsername = message.getRecipient();
        // Implement unbanning logic (e.g., re-add user to active users)
        User recipient = new User();
        recipient.setUsername(recipientUsername);
        users.put(recipientUsername, recipient);
        return true;
    }

    // Method to set title
    public boolean setTitle(Message message) {
        // Implement setting title logic
        return true;
    }

    // Method to set description
    public boolean setDescription(Message message) {
        // Implement setting description logic
        return true;
    }

    // Additional helper methods to manage users and roles
}
