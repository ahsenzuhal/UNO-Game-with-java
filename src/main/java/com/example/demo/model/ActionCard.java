package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("action")
public class ActionCard extends Card {
    @JsonCreator
    public ActionCard(@JsonProperty("color") Color color, @JsonProperty("value") int value) { super(color, value); }

    @Override
    public boolean canPlayOn(Card topCard) {
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue();
    }

    @Override
    public void applyEffect(GameService game) {
        if (this.getValue() == 10) { // 10 = Skip
            game.skipTurn();
        } else if (this.getValue() == 11) { // 11 = Draw Two
            game.penaltyDraw(2);
            game.skipTurn(); 
        }
    }
}