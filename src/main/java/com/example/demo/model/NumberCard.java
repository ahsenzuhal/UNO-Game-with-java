package com.example.demo.model;

import com.example.demo.service.GameService;

public class NumberCard extends Card {
    public NumberCard(Color color, int value) {
        super(color, value);
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        // Renk uyumu, sayı uyumu veya yerdeki kartın Joker olup olmaması kontrolü
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue();
    }

    @Override
    public void applyEffect(GameService game) {
        // Sayı kartı atıldıktan sonra sırayı diğerine devret
        game.moveToNextPlayer();
    }
}