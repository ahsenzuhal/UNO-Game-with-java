package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("number")
public class NumberCard extends Card {
    @JsonCreator
    public NumberCard(@JsonProperty("color") Color color, @JsonProperty("value") int value) {
        super(color, value);
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue();
    }

    @Override
    public void applyEffect(GameService game) {
        game.moveToNextPlayer();
    }
}