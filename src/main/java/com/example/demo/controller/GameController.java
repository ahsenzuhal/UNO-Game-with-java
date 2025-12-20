package com.example.demo.controller;

import com.example.demo.dto.GameStatusResponse;
import com.example.demo.dto.PlayerActionRequest;
import com.example.demo.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate; // Mesajları dağıtmak için

    @MessageMapping("/game.play")
    public void handlePlayerAction(@Payload PlayerActionRequest request) {
        boolean success = false;

        // İstemciden gelen aksiyon tipine göre servisi çağır
        switch (request.getActionType()) {
            case PLAY_CARD:
                success = gameService.playCard(request.getPlayerId(), request.getSelectedCard());
                break;
            case DRAW_CARD:
                // gameService.drawCardForPlayer(request.getPlayerId());
                success = true; 
                break;
            case SELECT_COLOR:
                gameService.handleColorSelection(request.getChosenColor());
                success = true;
                break;
        }

        if (success) {
            // Hamle başarılıysa, yeni oyun durumunu HERKESE gönder
            broadcastGameState();
        }
    }

    private void broadcastGameState() {
        // Burada her iki oyuncuya da güncel durumu yayınlıyoruz
        // İleride burayı özelleştirip her oyuncuya kendi elini göreceği şekilde güncelleyeceğiz
        messagingTemplate.convertAndSend("/topic/game-state", "Oyun durumu güncellendi!");
    }
}