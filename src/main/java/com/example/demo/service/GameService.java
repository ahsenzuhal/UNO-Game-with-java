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
            return newRoom;
        });

        if (room.getPlayers().size() < 2) {
            room.addPlayer(new Player(playerId, playerName));
            if (room.getPlayers().size() == 2) setupGame(roomId);
            return roomId;
        }
        return null;
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
        room.setCurrentActiveColor(firstCard.getColor());
        room.setGameStarted(true);
    }

    public boolean playCard(String roomId, String playerId, Card card, Color chosenColor) {
        GameRoom room = rooms.get(roomId);
        if (room == null || !room.isGameStarted()) return false;

        Player currentPlayer = room.getPlayers().get(room.getCurrentPlayerIndex());
        if (!currentPlayer.getId().equals(playerId)) return false;

        // Kartın geçerlilik kontrolü (Yerdeki aktif renge göre)
        if (!card.canPlayOn(room.getTopCard()) && card.getColor() != room.getCurrentActiveColor() && card.getColor() != Color.WILD) {
             // Eğer özel bir canPlayOn mantığın yoksa rengi de kontrol etmelisin
        }

        room.setTopCard(card);
        // Aktif rengi güncelle (Kilitlenmeyi önleyen kısım)
        if (card instanceof WildCard && chosenColor != null) {
            room.setCurrentActiveColor(chosenColor);
        } else {
            room.setCurrentActiveColor(card.getColor());
        }

        currentPlayer.getHand().removeIf(c -> c.getColor() == card.getColor() && c.getValue() == card.getValue());
        card.applyEffect(this, roomId);

        // Sıra Yönetimi
        if (card instanceof ActionCard && card.getValue() == 10) {
            // Skip: Sıra atan kişide kalır
        } else if (!(card instanceof WildCard && card.getValue() == 13)) {
            moveToNextPlayer(roomId);
        }

        if (currentPlayer.getHand().isEmpty()) room.setGameStarted(false);
        return true;
    }

    public GameStatusResponse getGameState(String roomId, String playerId) {
        GameRoom room = rooms.get(roomId);
        if (room == null) return null;

        Player viewer = room.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst().orElse(null);
        Player currentTurnPlayer = room.getPlayers().get(room.getCurrentPlayerIndex());

        String status = !room.isGameStarted() ? (room.getPlayers().size() < 2 ? "BEKLENİYOR" : "TAMAMLANDI") : "AKTİF";

        return GameStatusResponse.builder()
                .currentPlayerId(currentTurnPlayer.getId())
                .currentPlayerName(currentTurnPlayer.getName())
                .topCard(room.getTopCard())
                .activeColor(room.getCurrentActiveColor())
                .playerHand(viewer != null ? viewer.getHand() : new ArrayList<>())
                .statusMessage(status)
                .build();
    }

    public void drawCardForPlayer(String roomId, String playerId) {
        GameRoom room = rooms.get(roomId);
        if (room == null || !room.isGameStarted()) return;
        Player current = room.getPlayers().get(room.getCurrentPlayerIndex());
        if (current.getId().equals(playerId)) {
            current.getHand().add(room.getDeck().drawCard());
            moveToNextPlayer(roomId);
        }
    }

    public void moveToNextPlayer(String roomId) {
        GameRoom room = rooms.get(roomId);
        if (room != null) room.setCurrentPlayerIndex((room.getCurrentPlayerIndex() + 1) % room.getPlayers().size());
    }

    public void skipTurn(String roomId) { moveToNextPlayer(roomId); }
    public void penaltyDraw(String roomId, int count) {
        GameRoom room = rooms.get(roomId);
        Player victim = room.getPlayers().get((room.getCurrentPlayerIndex() + 1) % room.getPlayers().size());
        for (int i = 0; i < count; i++) victim.getHand().add(room.getDeck().drawCard());
    }
    public GameRoom getRoom(String roomId) { return rooms.get(roomId); }
}