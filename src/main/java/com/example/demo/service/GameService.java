package com.example.demo.service;

import com.example.demo.dto.GameStatusResponse;
import com.example.demo.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public String joinOrCreateRoom(String roomId, String playerId, String playerName) {
        GameRoom room = rooms.computeIfAbsent(roomId, id -> {
            GameRoom newRoom = new GameRoom();
            newRoom.setRoomId(id);
            System.out.println("Yeni Oda Oluşturuldu: " + id);
            return newRoom;
        });

        if (room.getPlayers().size() < 2) {
            room.addPlayer(new Player(playerId, playerName));
            if (room.getPlayers().size() == 2) setupGame(roomId);
            return roomId;
        }
        return null; // Oda doluysa
    }

    public void setupGame(String roomId) {
        GameRoom room = rooms.get(roomId);
        if (room == null) return;

        room.setDeck(new Deck());
        for (Player p : room.getPlayers()) {
            p.getHand().clear();
            for (int i = 0; i < 7; i++) p.getHand().add(room.getDeck().drawCard());
        }
        
        Card firstCard = room.getDeck().drawCard();
        while(firstCard.getColor() == Color.WILD) firstCard = room.getDeck().drawCard();
        room.setTopCard(firstCard);
        room.setGameStarted(true);
    }

    public GameStatusResponse getGameState(String roomId, String playerId) {
        GameRoom room = rooms.get(roomId);
        if (room == null) return null;

        Player player = room.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst().orElse(null);
        String currentTurnId = room.isGameStarted() ? room.getPlayers().get(room.getCurrentPlayerIndex()).getId() : "BEKLENİYOR";

        return GameStatusResponse.builder()
                .currentPlayerId(currentTurnId)
                .topCard(room.getTopCard())
                .activeColor(room.getCurrentActiveColor())
                .playerHand(player != null ? player.getHand() : new ArrayList<>())
                .opponentCardCount(room.getPlayers().stream().filter(p -> !p.getId().equals(playerId)).mapToInt(p -> p.getHand().size()).sum())
                .statusMessage(room.isGameStarted() ? "Oyun Odası: " + roomId : "Rakip Bekleniyor...")
                .build();
    }

    public GameRoom getRoom(String roomId) { return rooms.get(roomId); }

    // Eski sınıfların (ActionCard vb.) hata vermemesi için yönlendirici metodlar
public void skipTurn() { 
    // Bu metod şu an boş kalabilir veya aktif odayı bulmaya çalışabilir
    // Şimdilik derleme hatasını çözmek için ekliyoruz
}

public void penaltyDraw(int count) { }

public void moveToNextPlayer() { }

public List<Player> getPlayers() { 
    return new ArrayList<>(); 
}

public void addPlayer(String id, String name) {
    joinOrCreateRoom("default", id, name);
}
}