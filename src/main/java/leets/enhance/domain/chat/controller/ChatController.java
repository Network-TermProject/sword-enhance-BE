package leets.enhance.domain.chat.controller;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> activeUsers = ConcurrentHashMap.newKeySet();

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            activeUsers.remove(username);
            messagingTemplate.convertAndSend("/topic/activeUsers", activeUsers);
            System.out.println("Disconnected: " + username);
        }
    }

    @MessageMapping("/register")
    @SendTo("/topic/activeUsers")
    public Set<String> registerUser(String username) {
        activeUsers.add(username);
        return activeUsers;
    }

    @MessageMapping("/unregister")
    @SendTo("/topic/activeUsers")
    public Set<String> unregisterUser(String username) {
        activeUsers.remove(username);
        return activeUsers;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return message;
    }
}
