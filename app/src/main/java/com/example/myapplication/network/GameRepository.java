package com.example.myapplication.network;

import gr.aueb.dist.shared.BetRequest;
import gr.aueb.dist.shared.Game;
import gr.aueb.dist.shared.GameInfo;
import gr.aueb.dist.shared.Message;
import gr.aueb.dist.shared.SearchFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRepository {
    private final String host;
    private final int port;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    public GameRepository(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void searchGames(SearchFilter filter, Callback<List<GameInfo>> callback) {
        executor.execute(() -> {
            try {
                Message response = sendRequest(new Message("PLAYER_SEARCH", "", filter));
                if ("SUCCESS".equals(response.getType())) {
                    callback.onSuccess((List<GameInfo>) response.getPayload());
                } else {
                    callback.onError(new Exception(response.getContent()));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void playBet(BetRequest request, Callback<Game.BetResult> callback) {
        executor.execute(() -> {
            try {
                Message response = sendRequest(new Message("PLAYER_PLAY", request.getGameName(), request));
                if ("SUCCESS".equals(response.getType())) {
                    callback.onSuccess((Game.BetResult) response.getPayload());
                } else {
                    callback.onError(new Exception(response.getContent()));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void rateGame(String gameName, int stars, Callback<String> callback) {
        executor.execute(() -> {
            try {
                Message response = sendRequest(new Message("PLAYER_RATE", gameName, stars));
                if ("SUCCESS".equals(response.getType())) {
                    callback.onSuccess(response.getContent() != null ? response.getContent() : "Success");
                } else {
                    callback.onError(new Exception(response.getContent()));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void addBalance(String playerId, double amount, Callback<Double> callback) {
        executor.execute(() -> {
            try {
                Message response = sendRequest(new Message("PLAYER_ADD_BALANCE", playerId, Double.valueOf(amount)));
                if ("SUCCESS".equals(response.getType())) {
                    callback.onSuccess((Double) response.getPayload());
                } else {
                    callback.onError(new Exception(response.getContent()));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    private Message sendRequest(Message message) throws Exception {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(message);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (Message) in.readObject();
        }
    }
}
