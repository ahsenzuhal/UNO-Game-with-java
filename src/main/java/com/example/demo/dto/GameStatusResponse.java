package com.example.demo.dto;

import com.example.demo.model.Card;
import com.example.demo.model.Color;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GameStatusResponse {
    private String currentPlayerId;
    private Card topCard;
    private Color activeColor; // Wild kart atıldıysa seçilen renk
    private List<Card> playerHand; // Sadece ilgili oyuncunun göreceği kartlar
    private int opponentCardCount; // Rakibin kaç kartı kaldığı bilgisi
    private String statusMessage; // "Sıra sende!", "Ahsen kazandı!" gibi
}