package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("wild")
public class WildCard extends Card {
    public WildCard(int value) { super(Color.WILD, value); }

    @JsonCreator
    public WildCard(@JsonProperty("color") Color color, @JsonProperty("value") int value) {
        super(Color.WILD, value); //Burada renk değişkenine sabit değer atamamamızın nedeni rengin oyuncu tarafından belirlenecek oluşu.
    }

    @Override
    public boolean canPlayOn(Card topCard) { return true; } // Her zaman oynanır

    @Override
    public void applyEffect(GameService game, String roomId) {
        if (this.getValue() == 13) { // Wild Draw Four
            game.penaltyDraw(roomId, 4); //sıradaki oyuncuya +4 kart eklendi
            game.skipTurn(roomId); //oyuncunun sırası geçti.
        }
        // Renk seçimi logic'i frontend tarafında gönderilecek ve
        // GameService.playCard() tarafından currentActiveColor olarak atanacak.

        //Sen Joker attığında ekranında 4 renk çıkar. Sen "Mavi olsun" dersin.
        //Bu bilgi GameService'e gider ve yerdeki aktif renk artık Mavi olur.
    }
}