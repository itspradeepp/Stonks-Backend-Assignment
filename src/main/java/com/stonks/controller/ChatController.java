package com.stonks.controller;

import com.stonks.model.Message;
import com.stonks.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(Message message) {
        // Handle regular messages
        return message;
    }

    @MessageMapping("/setAdmin")
    @SendTo("/topic/public")
    public Message setAdmin(Message message) {
        if (chatService.setAdmin(message)) {
            message.setContent(message.getRecipient() + " has been granted admin rights by " + message.getSender());
        } else {
            message.setContent("Failed to set admin: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/unsetAdmin")
    @SendTo("/topic/public")
    public Message unsetAdmin(Message message) {
        if (chatService.unsetAdmin(message)) {
            message.setContent(message.getRecipient() + " has been removed from admin rights by " + message.getSender());
        } else {
            message.setContent("Failed to unset admin: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/muteUser")
    @SendTo("/topic/public")
    public Message muteUser(Message message) {
        if (chatService.muteUser(message)) {
            message.setContent(message.getRecipient() + " has been muted by " + message.getSender());
        } else {
            message.setContent("Failed to mute user: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/unmuteUser")
    @SendTo("/topic/public")
    public Message unmuteUser(Message message) {
        if (chatService.unmuteUser(message)) {
            message.setContent(message.getRecipient() + " has been unmuted by " + message.getSender());
        } else {
            message.setContent("Failed to unmute user: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/banUser")
    @SendTo("/topic/public")
    public Message banUser(Message message) {
        if (chatService.banUser(message)) {
            message.setContent(message.getRecipient() + " has been banned by " + message.getSender());
        } else {
            message.setContent("Failed to ban user: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/unbanUser")
    @SendTo("/topic/public")
    public Message unbanUser(Message message) {
        if (chatService.unbanUser(message)) {
            message.setContent(message.getRecipient() + " has been unbanned by " + message.getSender());
        } else {
            message.setContent("Failed to unban user: " + message.getRecipient());
        }
        return message;
    }

    @MessageMapping("/setTitle")
    @SendTo("/topic/public")
    public Message setTitle(Message message) {
        if (chatService.setTitle(message)) {
            message.setContent("Title has been set to: " + message.getContent());
        } else {
            message.setContent("Failed to set title");
        }
        return message;
    }

    @MessageMapping("/setDescription")
    @SendTo("/topic/public")
    public Message setDescription(Message message) {
        if (chatService.setDescription(message)) {
            message.setContent("Description has been set to: " + message.getContent());
        } else {
            message.setContent("Failed to set description");
        }
        return message;
    }
}
