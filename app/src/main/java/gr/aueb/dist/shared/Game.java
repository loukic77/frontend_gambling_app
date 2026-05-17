package gr.aueb.dist.shared;

import java.io.Serializable;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class BetResult implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String gameName;
        private final String playerId;
        private final double betAmount;
        private final double payout;
        private final double playerNetProfitLoss;
        private final double houseNetProfitLoss;
        private final boolean jackpotHit;
        private final int multiplierIndex;
        private final int randomNumber;
        private final double remainingBalance;

        public BetResult(
                String gameName,
                String playerId,
                double betAmount,
                double payout,
                double playerNetProfitLoss,
                double houseNetProfitLoss,
                boolean jackpotHit,
                int multiplierIndex,
                int randomNumber
        ) {
            this.gameName = gameName;
            this.playerId = playerId;
            this.betAmount = betAmount;
            this.payout = payout;
            this.playerNetProfitLoss = playerNetProfitLoss;
            this.houseNetProfitLoss = houseNetProfitLoss;
            this.jackpotHit = jackpotHit;
            this.multiplierIndex = multiplierIndex;
            this.randomNumber = randomNumber;
            this.remainingBalance = Double.NaN;
        }

        public BetResult(
                String gameName,
                String playerId,
                double betAmount,
                double payout,
                double playerNetProfitLoss,
                double houseNetProfitLoss,
                boolean jackpotHit,
                int multiplierIndex,
                int randomNumber,
                double remainingBalance
        ) {
            this.gameName = gameName;
            this.playerId = playerId;
            this.betAmount = betAmount;
            this.payout = payout;
            this.playerNetProfitLoss = playerNetProfitLoss;
            this.houseNetProfitLoss = houseNetProfitLoss;
            this.jackpotHit = jackpotHit;
            this.multiplierIndex = multiplierIndex;
            this.randomNumber = randomNumber;
            this.remainingBalance = remainingBalance;
        }

        public String getGameName() {
            return gameName;
        }

        public String getPlayerId() {
            return playerId;
        }

        public double getBetAmount() {
            return betAmount;
        }

        public double getPayout() {
            return payout;
        }

        public double getPlayerNetProfitLoss() {
            return playerNetProfitLoss;
        }

        public double getHouseNetProfitLoss() {
            return houseNetProfitLoss;
        }

        public boolean isJackpotHit() {
            return jackpotHit;
        }

        public int getMultiplierIndex() {
            return multiplierIndex;
        }

        public int getRandomNumber() {
            return randomNumber;
        }

        public double getRemainingBalance() {
            return remainingBalance;
        }

        public double getNetProfitLoss() {
            return playerNetProfitLoss;
        }
    }
}
