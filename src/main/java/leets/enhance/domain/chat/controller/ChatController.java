package leets.enhance.domain.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

    private final Set<String> activeUsers = ConcurrentHashMap.newKeySet();

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
