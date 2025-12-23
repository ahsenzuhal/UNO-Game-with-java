package com.example.demo.controller;

import java.util.Map;
import com.example.demo.service.GameService;
import com.example.demo.model.GameRoom;
import com.example.demo.model.Card;
import com.example.demo.model.Color;
import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper için gerekli
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;
    
    // Değişiklik burada: final takısını kaldırıp @Autowired ekledik
    @org.springframework.beans.factory.annotation.Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @MessageMapping("/game.join")
    public void joinRoom(@Payload Map<String, String> message) {
        String roomId = message.get("roomId");
        String playerId = message.get("playerId");
        String playerName = message.get("playerName");

        gameService.joinOrCreateRoom(roomId, playerId, playerName);
        broadcastGameState(roomId);
    }

    @MessageMapping("/game.play")
    public void playAction(@Payload Map<String, Object> message) {
        String roomId = (String) message.get("roomId");
        String playerId = (String) message.get("playerId");
        String actionType = (String) message.get("actionType");

        try {
            if ("PLAY_CARD".equals(actionType)) {
                // Convert selected card and optional chosenColor (for Wild)
                Card card = objectMapper.convertValue(message.get("selectedCard"), Card.class);
                Color chosenColor = null;
                if (message.get("chosenColor") != null) {
                    try {
                        chosenColor = Color.valueOf(((String) message.get("chosenColor")).toUpperCase());
                    } catch (Exception ignored) {}
                }
                gameService.playCard(roomId, playerId, card, chosenColor);
            } else if ("DRAW_CARD".equals(actionType)) {
                gameService.drawCardForPlayer(roomId, playerId);
            }
        } catch (Exception e) {
            System.err.println("Hamle işlenirken hata oluştu: " + e.getMessage());
        }

        broadcastGameState(roomId);
    }

    private void broadcastGameState(String roomId) {
        GameRoom room = gameService.getRoom(roomId);
        if (room == null) return;

        room.getPlayers().forEach(player -> {
            messagingTemplate.convertAndSend(
                "/topic/game-state/" + roomId + "/" + player.getId(), 
                gameService.getGameState(roomId, player.getId())
            );
        });
    }
}