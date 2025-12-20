package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("wild")
public class WildCard extends Card {

    @JsonCreator
    public WildCard(@JsonProperty("value") int value) { // 12 = Wild, 13 = Wild Draw Four
        super(Color.WILD, value);
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return true; // Joker her zaman oynanır!
    }

    @Override
    public void applyEffect(GameService game) {
        if (this.getValue() == 13) {
            game.penaltyDraw(4);
            game.skipTurn();
        }
    }
}