package com.andersenlab.listener;

import com.andersenlab.util.ClientMessage;
import com.andersenlab.util.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collections;

/**
 * @author Sergei Nemogai
 * created at 17.01.2020
 */

@Component
public class WebSocketEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);
    private SimpMessageSendingOperations messagingTemplate;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            LOGGER.info("User Disconnected: " + username);
            ClientMessage message = new ClientMessage(username, " disconnected");
            messagingTemplate.convertAndSend("/topic/chat",
                    new ServerMessage(username, Collections.singletonList(message)));
        }
    }
}
