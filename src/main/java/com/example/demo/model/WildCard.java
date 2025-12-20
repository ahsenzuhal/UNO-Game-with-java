package com.example.demo.model;
import com.example.demo.service.GameService;

public class WildCard extends Card {

    public WildCard(int value) { // 12 = Wild, 13 = Wild Draw Four
        super(Color.WILD, value);
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return true; // Joker her zaman oynanır!
    }

    @Override
    public void applyEffect(GameService game) {
        // Not: Renk seçimi WebSocket'ten gelen ayrı bir veri olacak.
        // Ama eğer +4 ise ceza işlemini burada tetikleyebiliriz.
        if (this.getValue() == 13) {
            game.penaltyDraw(4);
            game.skipTurn();
        }
        // Normal Wild (12) için sadece renk seçimi beklenir, 
        // sıra hemen geçmez (çünkü önce renk seçilmeli).
    }
}