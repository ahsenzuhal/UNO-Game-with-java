package com.example.demo.model;
import com.example.demo.service.GameService;


import lombok.Getter;

@Getter // Veya elinle getter metotlarını yaz
public abstract class Card {
    private final Color color;
    private final int value;

    public Card(Color color, int value) { // Bu constructor mutlaka olmalı
        this.color = color;
        this.value = value;
    }
    public abstract boolean canPlayOn(Card topCard);
    public abstract void applyEffect(GameService game);
}
