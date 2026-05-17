package com.example.myapplication.model;

import java.io.Serializable;

public class BetResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gameName;
    private String playerId;
    private double betAmount;
    private double payout;
    private double netProfitLoss;
    private double houseNetProfitLoss;
    private boolean jackpotHit;
    private int randomNumber;

    // Getters
    public String getGameName() { return gameName; }
    public String getPlayerId() { return playerId; }
    public double getBetAmount() { return betAmount; }
    public double getPayout() { return payout; }
    public double getNetProfitLoss() { return netProfitLoss; }
    public double getHouseNetProfitLoss() { return houseNetProfitLoss; }
    public boolean isJackpotHit() { return jackpotHit; }
    public int getRandomNumber() { return randomNumber; }
}
