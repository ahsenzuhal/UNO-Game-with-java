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
public abstract class Card {  //abstract sınıf olarak tanımladık çünkü metotlar alt sınıflarda doldurulacak. Bu sadece bir sözleşme
    private final Color color; //her kartın istisnasız bir rengi olacak (Color enum sınıfında tutuyoruz renkleri)
    private final int value; // her kartın istisnasız bir değeri olacak.
  
    @JsonCreator  //burada frontend tarafında kartın hangi etiket ile okunacağını belirliyoruz. 
    public Card(@JsonProperty("color") Color color, @JsonProperty("value") int value) {
        this.color = color;
        this.value = value;
    }

    public abstract boolean canPlayOn(Card topCard);  //canPlayOn metodu alt sınıflarda @override edilecek. Bu metod yerdeki kartın üzerine atılmak istenen kartın atılabilirliğini kontrol edecek.

    public abstract void applyEffect(GameService game, String roomId); //Burada da kart atıldığında ne olacağını yazacağız. (Oyuncu atlanacak mı? Desteden kart mı çekecek? vb.)

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}