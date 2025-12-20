package com.example.demo.model;
import com.example.demo.service.*;


public class ActionCard extends Card {
    public ActionCard(Color color, int value) { super(color, value); }

    @Override
    public boolean canPlayOn(Card topCard) {
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue();
    }

    @Override
    public void applyEffect(GameService game) {
        if (this.getValue() == 10) { // Varsayalım 10 = Skip
            game.skipTurn();
        } else if (this.getValue() == 11) { // 11 = Draw Two
            game.penaltyDraw(2);
            game.skipTurn(); 
        }
    }
}