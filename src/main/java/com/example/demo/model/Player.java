package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data //Bu tek başına; sınıftaki tüm değişkenler için getter, setter, equals, hashCode ve toString metotlarını arka planda otomatik oluşturur.
@NoArgsConstructor //Parametresiz, boş bir yapıcı metot oluşturur. Eğer bu boş metot olmazsa, bazı kütüphaneler "Ben bu nesneyi nasıl yaratacağımı bilemiyorum" diyerek hata verir.
@AllArgsConstructor //Tüm değişkenleri (id, name, hand) içeren bir yapıcı metot oluşturur.

public class Player {
    private String id;
    private String name;
    private List<Card> hand = new ArrayList<>(); //Oyuncunun elindeki kartları bir liste olarak tutuyoruz. 
    // Listenin tipi Card. Bu sayede bu listenin içine hem NumberCard, hem ActionCard, hem de WildCard koyabiliyoruz.
    // handi oyuncu oluştuğu an oluşturuyoruz diğer türlü hata alırız.

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }
}