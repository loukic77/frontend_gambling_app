package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.GameRepository;
import shared.Game;

public class PlayActivity extends AppCompatActivity {

    private GameViewModel viewModel;
    private View progressBar;
    private View resultCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        GameRepository repository = new GameRepository("10.0.2.2", 6000);
        viewModel = new GameViewModel(repository);

        progressBar = findViewById(R.id.progress_bar);
        resultCard = findViewById(R.id.result_card);

        EditText etPlayerId = findViewById(R.id.et_player_id);
        EditText etGameName = findViewById(R.id.et_game_name);
        EditText etAmount = findViewById(R.id.et_amount);

        String prefillGame = getIntent().getStringExtra("gameName");
        if (prefillGame != null) etGameName.setText(prefillGame);

        findViewById(R.id.btn_play).setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
            String pId = etPlayerId.getText().toString();
            String gName = etGameName.getText().toString();
            double amt = Double.parseDouble(etAmount.getText().toString());
            viewModel.playBet(pId, gName, amt);
        });

        viewModel.getBetState().observe(this, state -> {
            switch (state.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    resultCard.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    resultCard.setVisibility(View.VISIBLE);
                    displayResult(state.data);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error: " + state.errorMessage, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void displayResult(Game.BetResult res) {
        ((TextView)findViewById(R.id.tv_payout)).setText(String.format("$%.2f", res.getPayout()));
        TextView tvNetProfit = findViewById(R.id.tv_net_profit);
        tvNetProfit.setText(String.format("$%.2f", res.getNetProfitLoss()));
        tvNetProfit.setTextColor(res.getNetProfitLoss() >= 0 ? 0xFFAEEA00 : 0xFFE53E3E);
        ((TextView)findViewById(R.id.tv_jackpot_hit)).setText(res.isJackpotHit() ? "YES! 🎉" : "NO");
        ((TextView)findViewById(R.id.tv_random_num)).setText(String.valueOf(res.getRandomNumber()));
        
        TextView tvBalance = findViewById(R.id.tv_balance);
        if (!Double.isNaN(res.getRemainingBalance())) {
            tvBalance.setText(String.format("$%.2f", res.getRemainingBalance()));
            tvBalance.setVisibility(View.VISIBLE);
        } else {
            tvBalance.setText("N/A");
        }
    }
}
