package com.example.demo.model;

import lombok.Data;
import java.util.*;

@Data
public class GameRoom {
    private String roomId; //Birden fazla oyununu aynı anda oynanmasını burası sağlayacak
    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck(); //Kapalı kart nesnesi
    private Card topCard; // en üste atılmış kart
    private int currentPlayerIndex = 0; // "Sıra kimde?" bilgisini tutar. Örneğin 0 ise listedeki ilk oyuncunun, 1 ise ikincinin sırasıdır.
    private boolean isGameStarted = false; //Oyun başladı mı yoksa hala oyuncu mu bekleniyor?
    private Color currentActiveColor; //Joker atıldığında belirlenen "yeni rengi" burada tutarız.

    public void addPlayer(Player player) { //Burada yeni bir oyuncu masaya geldiğinde yapılacak kontrolleri yapıyoruz.
        if (players.size() >= 2) return; //Eğer masada zaten 2 kişi varsa 3. bir kişinin masaya oturmasına izin verilmez

        boolean exists = players.stream().anyMatch(p -> p.getId().equals(player.getId())); //Masada zaten bu ID'ye sahip biri var mı?
        if (!exists) players.add(player);
    }
}