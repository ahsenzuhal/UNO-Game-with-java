package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private Card topCard;
    private int currentPlayerIndex = 0;
    private boolean isGameStarted = false;
    private Color currentActiveColor;

    // Oyuncu ekleme
    public void addPlayer(String id, String name) {
        if (players.size() < 2) {
            players.add(new Player(id, name));
        }
    }

    // Kart oynama mantığı
    public boolean playCard(String playerId, Card card) {
        Player currentPlayer = players.get(currentPlayerIndex);
        
        if (!currentPlayer.getId().equals(playerId)) return false;
        Color colorToMatch = (topCard.getColor() == Color.WILD) ? currentActiveColor : topCard.getColor();

        if (card.canPlayOn(topCard) || card.getColor() == colorToMatch) {
            topCard = card;
            currentPlayer.getHand().remove(card);
            
            // OOP: Polimorfizm - Kartın kendi etkisini tetikle
            card.applyEffect(this);
            
            return true;
        }
        return false;
    }

    // --- ActionCard ve WildCard'ın çağırdığı yardımcı metodlar ---

    public void skipTurn() {
        // Sırayı bir kez daha kaydırarak bir sonraki oyuncuyu atla
        moveToNextPlayer();
        System.out.println("Sıra atlatıldı!");
    }

    public void penaltyDraw(int count) {
        // Bir sonraki oyuncuyu belirle
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player victim = players.get(nextPlayerIndex);
        
        for (int i = 0; i < count; i++) {
            Card drawn = deck.drawCard();
            if (drawn != null) victim.getHand().add(drawn);
        }
    }

    public void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    // Yerdeki kartı güncellemek için (Wild kartlarda renk seçimi sonrası)
    public void setTopCard(Card card) {
        this.topCard = card;
    }


    // Renk seçimi yapıldığında çağrılır
    public void handleColorSelection(Color color) {
    this.currentActiveColor = color;
    moveToNextPlayer(); // Renk seçildikten sonra sıra geçer
    }

    public void setupGame() {
    deck = new Deck();
    // İki oyuncuya 7'şer kart dağıt
    for (Player player : players) {
        player.getHand().clear(); // Sıfırla
        for (int i = 0; i < 7; i++) {
            player.getHand().add(deck.drawCard());
        }
    }
    // Yere ilk kartı aç (Sayı kartı gelene kadar çekmek en iyisidir)
    topCard = deck.drawCard();
    while(topCard.getColor() == Color.WILD) { // İlk kart joker olmasın
        topCard = deck.drawCard();
    }
    isGameStarted = true;
}
}

    