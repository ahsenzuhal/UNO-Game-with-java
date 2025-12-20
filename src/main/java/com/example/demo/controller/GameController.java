package com.example.demo.controller;


import java.util.Map;
import com.example.demo.service.GameService;
import com.example.demo.model.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game.join")
    public void joinRoom(@Payload Map<String, String> message) {
        String roomId = message.get("roomId");
        String playerId = message.get("playerId");
        String playerName = message.get("playerName");

        gameService.joinOrCreateRoom(roomId, playerId, playerName);
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