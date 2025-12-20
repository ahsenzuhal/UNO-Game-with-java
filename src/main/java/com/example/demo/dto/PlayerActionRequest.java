package com.example.demo.dto;

import com.example.demo.model.Card;
import com.example.demo.model.Color;
import lombok.Data;

@Data
public class PlayerActionRequest {
    private String playerId;
    private ActionType actionType; // PLAY_CARD, DRAW_CARD, SELECT_COLOR
    private Card selectedCard;
    private Color chosenColor; // Sadece Wild kart atıldıysa dolu gelir

    public enum ActionType {
        PLAY_CARD, DRAW_CARD, SELECT_COLOR
    }
}