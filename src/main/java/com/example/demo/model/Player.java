package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String id;
    private String name;
    private List<Card> hand = new ArrayList<>();

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }
}