package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("action")
public class ActionCard extends Card {
    @JsonCreator
    public ActionCard(@JsonProperty("color") Color color, @JsonProperty("value") int value) { 
        super(color, value); 
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue();
    }

    @Override

    //Bu metodda game parametresinin olmasınınn sebebi
    public void applyEffect(GameService game, String roomId) { //Bu metot GameService ile konuşarak oyunun akışını değiştiriyor
        if (this.getValue() == 10) { // eğer değer 10 ise kartı 'Skip' olarak ayarladık. 
            game.skipTurn(roomId); // bir sonraki oyuncunun sırasını atla
        } else if (this.getValue() == 11) { //eğer değer 11 ise 'Draw Two' olarak ayarladık.
            game.penaltyDraw(roomId, 2); //Bir sonraki oyuncuya 2 kart ekler
            game.skipTurn(roomId);  //ve sıradaki oyuncunun sırası geçer
        }
    }
}