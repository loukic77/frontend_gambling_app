package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import shared.GameInfo;
import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<GameInfo> games = new ArrayList<>();
    private final OnGameClickListener listener;

    public interface OnGameClickListener {
        void onPlayClick(GameInfo game);
    }

    public GameAdapter(OnGameClickListener listener) {
        this.listener = listener;
    }

    public void setGames(List<GameInfo> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameInfo game = games.get(position);
        holder.tvName.setText(game.getGameName());
        holder.tvProvider.setText(game.getProviderName());
        holder.tvRating.setText("⭐ " + game.getStars());
        holder.tvCategory.setText(game.getBetCategory());
        holder.tvRisk.setText(game.getRiskLevel());
        holder.tvJackpot.setText("$" + String.format("%.2f", game.getJackpot()));
        holder.tvActive.setText(game.isActive() ? "ACTIVE" : "INACTIVE");
        holder.tvActive.setTextColor(game.isActive() ? 0xFF48BB78 : 0xFFE53E3E);

        holder.btnPlay.setOnClickListener(v -> listener.onPlayClick(game));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvProvider, tvRating, tvCategory, tvRisk, tvJackpot, tvActive;
        View btnPlay;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_game_name);
            tvProvider = itemView.findViewById(R.id.tv_provider);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvRisk = itemView.findViewById(R.id.tv_risk);
            tvJackpot = itemView.findViewById(R.id.tv_jackpot);
            tvActive = itemView.findViewById(R.id.tv_active_status);
            btnPlay = itemView.findViewById(R.id.btn_play_item);
        }
    }
}
