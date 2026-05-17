package com.example.myapplication.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.network.GameRepository;
import gr.aueb.dist.shared.BetRequest;
import gr.aueb.dist.shared.Game;
import gr.aueb.dist.shared.GameInfo;
import gr.aueb.dist.shared.SearchFilter;
import java.util.List;

public class GameViewModel extends ViewModel {
    public enum Status { IDLE, LOADING, SUCCESS, ERROR }

    public static class UIState<T> {
        public final Status status;
        public final T data;
        public final String errorMessage;

        public UIState(Status status, T data, String errorMessage) {
            this.status = status;
            this.data = data;
            this.errorMessage = errorMessage;
        }

        public static <T> UIState<T> idle() { return new UIState<>(Status.IDLE, null, null); }
        public static <T> UIState<T> loading() { return new UIState<>(Status.LOADING, null, null); }
        public static <T> UIState<T> success(T data) { return new UIState<>(Status.SUCCESS, data, null); }
        public static <T> UIState<T> error(String msg) { return new UIState<>(Status.ERROR, null, msg); }
    }

    private final GameRepository repository;
    private final MutableLiveData<UIState<List<GameInfo>>> searchState = new MutableLiveData<>(UIState.idle());
    private final MutableLiveData<UIState<Game.BetResult>> betState = new MutableLiveData<>(UIState.idle());
    private final MutableLiveData<UIState<String>> rateState = new MutableLiveData<>(UIState.idle());
    private final MutableLiveData<UIState<Double>> balanceState = new MutableLiveData<>(UIState.idle());

    public GameViewModel(GameRepository repository) {
        this.repository = repository;
    }

    public LiveData<UIState<List<GameInfo>>> getSearchState() { return searchState; }
    public LiveData<UIState<Game.BetResult>> getBetState() { return betState; }
    public LiveData<UIState<String>> getRateState() { return rateState; }
    public LiveData<UIState<Double>> getBalanceState() { return balanceState; }

    public void searchGames(int minStars, String risk, String category) {
        searchState.setValue(UIState.loading());
        repository.searchGames(new SearchFilter(minStars, risk, category), new GameRepository.Callback<List<GameInfo>>() {
            @Override
            public void onSuccess(List<GameInfo> result) {
                searchState.postValue(UIState.success(result));
            }

            @Override
            public void onError(Exception e) {
                searchState.postValue(UIState.error(e.getMessage()));
            }
        });
    }

    public void playBet(String playerId, String gameName, double amount) {
        betState.setValue(UIState.loading());
        repository.playBet(new BetRequest(playerId, gameName, amount), new GameRepository.Callback<Game.BetResult>() {
            @Override
            public void onSuccess(Game.BetResult result) {
                betState.postValue(UIState.success(result));
            }

            @Override
            public void onError(Exception e) {
                betState.postValue(UIState.error(e.getMessage()));
            }
        });
    }

    public void rateGame(String gameName, int stars) {
        rateState.setValue(UIState.loading());
        repository.rateGame(gameName, stars, new GameRepository.Callback<String>() {
            @Override
            public void onSuccess(String result) {
                rateState.postValue(UIState.success(result));
            }

            @Override
            public void onError(Exception e) {
                rateState.postValue(UIState.error(e.getMessage()));
            }
        });
    }

    public void addBalance(String playerId, double amount) {
        balanceState.setValue(UIState.loading());
        repository.addBalance(playerId, amount, new GameRepository.Callback<Double>() {
            @Override
            public void onSuccess(Double result) {
                balanceState.postValue(UIState.success(result));
            }

            @Override
            public void onError(Exception e) {
                balanceState.postValue(UIState.error(e.getMessage()));
            }
        });
    }

    public void resetStates() {
        searchState.setValue(UIState.idle());
        betState.setValue(UIState.idle());
        rateState.setValue(UIState.idle());
        balanceState.setValue(UIState.idle());
    }
}
