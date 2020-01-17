package com.andersenlab.controller;

import com.andersenlab.model.Message;
import com.andersenlab.model.User;
import com.andersenlab.repository.MessageRepository;
import com.andersenlab.repository.UserRepository;
import com.andersenlab.util.ClientMessage;
import com.andersenlab.util.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sergei Nemogai
 * created at 15.01.2020
 */

@Controller
public class ChatController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    public ChatController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // works only when logging is needed
    @MessageMapping("/login")
    @SendTo("/topic/chat")
    public ServerMessage joinChat(ClientMessage clientMessage, SimpMessageHeaderAccessor headerAccessor) {
        String text;
        User user;
        List<Message> messages = new ArrayList<>();
        String username = clientMessage.getUsername();
        // add user to current session
        headerAccessor.getSessionAttributes().put("username", username);
        LOGGER.info("User Connected:" + username);
        Optional<User> userOptional = userRepository.findByUsername(username);

        // checking the user existence
        if(!userOptional.isPresent()) {
            user = userRepository.save(User.builder().username(username).build());
            text = " joined the chat";
        } else {
            user = userOptional.get();
            text = " connected the chat";

            // get all messages have been sending after user's first message
            messages = messageRepository.findAll(
                    (Specification<Message>) (root, criteriaQuery, builder) -> builder.greaterThanOrEqualTo(
                            root.get("sendAt"), messageRepository.findFirstByUserOrderById(user).getSendAt()));
        }

        Message message = Message.builder()
                .text(username + text)
                .sendAt(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();
        messageRepository.save(message);

        ServerMessage serverMessages = new ServerMessage();
        serverMessages.setMessages(messages
                .stream().map(m -> new ClientMessage(m.getUser().getUsername(), m.getText()))
                .collect(Collectors.toList())
        );
        // to send status message
        serverMessages.getMessages().add(new ClientMessage(username, username + text));
        return serverMessages;
    }

    // for receiving messages for all subscribers
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ServerMessage sendMessage(ClientMessage clientMessage) {
        String username = clientMessage.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseGet(() -> userRepository.save(
                User.builder().username(username).build()));
        messageRepository.save(Message.builder()
                .text(clientMessage.getText())
                .sendAt(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build());
        return new ServerMessage(clientMessage.getUsername(), Collections.singletonList(clientMessage));
    }
}