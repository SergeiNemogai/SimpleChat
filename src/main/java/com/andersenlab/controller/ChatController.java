package com.andersenlab.controller;

import com.andersenlab.model.Message;
import com.andersenlab.model.User;
import com.andersenlab.repository.MessageRepository;
import com.andersenlab.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

/**
 * @author Sergei Nemogai
 * created at 15.01.2020
 */

@Controller
public class ChatController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @MessageMapping("/login")
    @SendTo("/topic/chat")
    public Message addUser(User user) {
        String text;
        if(!userRepository.findByUsername(user.getUsername()).isPresent()) {
            userRepository.save(user);
            text = " joined the chat";
        } else {
            text = " connected the chat";
        }
        Message message = Message.builder()
                .text(user.getUsername() + text)
                .sendAt(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();
        messageRepository.save(message);
        return message;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Message sendMessage(Message message) {
        messageRepository.save(message);
        return message;
    }
}
