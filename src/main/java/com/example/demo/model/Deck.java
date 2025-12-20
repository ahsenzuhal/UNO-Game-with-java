package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        initializeDeck(); // Tüm kart ekleme mantığını tek yere topladık
        shuffle();
    }

    private void initializeDeck() {
        cards.clear();
        for (Color c : Color.values()) {
            if (c == Color.WILD) continue;
            
            // 0-9 Sayı kartları (Her renkten ikişer tane olur genelde ama proje için birer yeterli)
            for (int i = 0; i <= 9; i++) {
                cards.add(new NumberCard(c, i));
            }
            
            // Aksiyonlar
            cards.add(new ActionCard(c, 10)); // Skip
            cards.add(new ActionCard(c, 11)); // Draw Two
        }
        
        // 4'er tane Joker (Wild) ekle
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(12)); // Wild
            cards.add(new WildCard(13)); // Wild Draw 4
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            // Eğer deste biterse yerden alıp tekrar karıştırma mantığı eklenebilir
            return null; 
        }
        return cards.remove(0);
    }
}