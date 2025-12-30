package com.example.demo.model;

import com.example.demo.service.GameService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeName("number")
public class NumberCard extends Card { //kart sınıfından kalıtım aldık.

    @JsonCreator //frontend bu kartı hangi etiket ile tanıyacak?
    public NumberCard(@JsonProperty("color") Color color, @JsonProperty("value") int value) { //Frontend'den gelen veride "type": "number" yazdığında, Spring Boot otomatik olarak "Tamam, bu bir NumberCard nesnesidir" der.
        super(color, value); //consturcter içerisinde üst sınıftan aldığımız bilgileri kullandık.
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return this.getColor() == topCard.getColor() || this.getValue() == topCard.getValue(); //eğer buKartınRengi == yerdekiRenk VEYA buKartınDeğeri == yerdekiDeğer ise kart oynanabilir.
    }

    @Override
    public void applyEffect(GameService game, String roomId) {
        // Sayı kartlarının özel bir etkisi olmadığı için bu metot boş
    }
}