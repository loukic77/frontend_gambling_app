package com.example.myapplication.model;

import java.io.Serializable;

public class BetRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerId;
    private String gameName;
    private double amount;

    public BetRequest(String playerId, String gameName, double amount) {
        this.playerId = playerId;
        this.gameName = gameName;
        this.amount = amount;
    }

    public String getPlayerId() { return playerId; }
    public String getGameName() { return gameName; }
    public double getAmount() { return amount; }
}
