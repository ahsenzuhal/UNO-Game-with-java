package com.example.demo.model;

import lombok.Data;
import java.util.*;

@Data
public class GameRoom {
    private String roomId;
    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private Card topCard;
    private int currentPlayerIndex = 0;
    private boolean isGameStarted = false;
    private Color currentActiveColor;

    public void addPlayer(Player player) {
        if (players.size() < 2) players.add(player);
    }
}