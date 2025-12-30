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
    

    @org.springframework.beans.factory.annotation.Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @MessageMapping("/game.join") //Oyuncu oyuna girdiğinde, kart attığında veya çektiğinde bu adrese mesaj atar.
    public void joinRoom(@Payload Map<String, String> message) { //Bir oyuncunun belirli bir odaya giriş yapmasını ve oyunun güncel halinden haberdar edilmesini sağlar.
        String roomId = message.get("roomId");
        String playerId = message.get("playerId");
        String playerName = message.get("playerName");

        gameService.joinOrCreateRoom(roomId, playerId, playerName); 
        broadcastGameState(roomId);
    }

    @MessageMapping("/game.play")
    public void playAction(@Payload Map<String, Object> message) { //Oyun sırasında oyuncudan gelen tüm fiziksel hamleleri (kart atma veya kart çekme) yöneten ana karar merkezidir.
        String roomId = (String) message.get("roomId");
        String playerId = (String) message.get("playerId");
        String actionType = (String) message.get("actionType");

        try {
            if ("PLAY_CARD".equals(actionType)) { //Eğer kart oynanacaksa:
                Card card = objectMapper.convertValue(message.get("selectedCard"), Card.class); //"Gelen veride 'mavi' ve '5' yazıyor, ha tamam bu bir NumberCard nesnesidir." Diyor"
                Color chosenColor = null; 
                if (message.get("chosenColor") != null) { //Oyuncuda bir renk seçtiyse
                    try {
                        chosenColor = Color.valueOf(((String) message.get("chosenColor")).toUpperCase());
                    } catch (Exception ignored) {}
                }
                gameService.playCard(roomId, playerId, card, chosenColor); //gameService.playCard metodunu tetikler.
            } 
            
            else if ("DRAW_CARD".equals(actionType)) {
                gameService.drawCardForPlayer(roomId, playerId); //gameService.drawCardForPlayer metodunu çağırarak oyuncunun yerden kart çekmesini sağlar.
            }

        } 
        
        catch (Exception e) {
            System.err.println("Hamle işlenirken hata oluştu: " + e.getMessage());
        }

        broadcastGameState(roomId); //son durumu tüm oyunculara duyurduk
    }

    private void broadcastGameState(String roomId) { //Oyunun o anki durumunu (yerdeki kart, sıradaki oyuncu, eldeki kartlar vb.) odadaki tüm oyunculara senkronize bir şekilde duyurur.
        GameRoom room = gameService.getRoom(roomId);
        if (room == null) return;

        room.getPlayers().forEach(player -> {
            messagingTemplate.convertAndSend( 
                "/topic/game-state/" + roomId + "/" + player.getId(), 
                gameService.getGameState(roomId, player.getId()) //Her oyuncu için gameService.getGameState metodunu kullanarak kişiye özel bir veri paketi hazırlar (Böylece kimse başkasının kartını göremez).
            );
        });
    }
}