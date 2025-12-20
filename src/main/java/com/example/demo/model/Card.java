package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NumberCard.class, name = "number"),
        @JsonSubTypes.Type(value = ActionCard.class, name = "action"),
        @JsonSubTypes.Type(value = WildCard.class, name = "wild")
})
public abstract class Card {
    private final Color color;
    private final int value;

    @JsonCreator
    public Card(@JsonProperty("color") Color color, @JsonProperty("value") int value) {
        this.color = color;
        this.value = value;
    }

    public abstract boolean canPlayOn(Card topCard);
    public abstract void applyEffect(GameService game);

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
