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

    private void initializeDeck() { //Deste nesnesi yaratıldığı an (new Deck()), bu metot çalışır ve sıfırdan bir Uno destesi oluşturur.
        cards.clear();
        for (Color c : Color.values()) { //number card ve action card oluşturacağız
            if (c == Color.WILD) continue; //eğer wild kartı ise burada oluşturma
            
            // 0-9 Sayı kartları (Her renkten birer tane oluşturuyoruz)
            for (int i = 0; i <= 9; i++) {
                cards.add(new NumberCard(c, i));
            }
            
            // Aksiyonlar
            cards.add(new ActionCard(c, 10)); // Skip oluşturuldu (her renk için)
            cards.add(new ActionCard(c, 11)); // Draw Two oluşturuldu (her renk için )
        }
        
        // 4'er tane Joker (Wild) ekle
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(12)); // Wild
            cards.add(new WildCard(13)); // Wild Draw 4
        }
    }

    public void shuffle() { //Listenin içindeki kartların yerini tamamen rastgele değiştirir.
        Collections.shuffle(cards); //javanın hazır kütüphanesini kullandık
    }

    public Card drawCard() { //Oyuncular "kart çek" dediğinde veya oyun başında kart dağıtılırken bu metot kullanılır:
        if (cards.isEmpty()) { //
            // Eğer deste biterse yerden alıp tekrar karıştırma mantığı eklenebilir
            return null; 
        }
        return cards.remove(0); //Listenin en başındaki (0. indeks) kartı alır, listeden siler ve oyuncuya teslim eder.
    }
}