package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.GameRepository;

public class AddBalanceActivity extends AppCompatActivity {

    private GameViewModel viewModel;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        GameRepository repository = new GameRepository("10.0.2.2", 6000);
        viewModel = new GameViewModel(repository);

        progressBar = findViewById(R.id.progress_bar);
        EditText etPlayerId = findViewById(R.id.et_player_id);
        EditText etAmount = findViewById(R.id.et_amount);

        findViewById(R.id.btn_add_balance).setOnClickListener(v -> {
            String playerId = etPlayerId.getText().toString();
            String amountStr = etAmount.getText().toString();
            
            if (playerId.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                viewModel.addBalance(playerId, amount);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getBalanceState().observe(this, state -> {
            switch (state.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Balance updated: " + state.data, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error: " + state.errorMessage, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }
}
