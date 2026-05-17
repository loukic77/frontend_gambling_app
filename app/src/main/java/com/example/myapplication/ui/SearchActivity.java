package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.network.GameRepository;

public class SearchActivity extends AppCompatActivity {

    private GameViewModel viewModel;
    private GameAdapter adapter;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // In a real app, host/port should come from config or shared prefs
        GameRepository repository = new GameRepository("10.0.2.2", 6000);
        viewModel = new GameViewModel(repository); 

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.rv_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameAdapter(game -> {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("gameName", game.getGameName());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        EditText etStars = findViewById(R.id.et_min_stars);
        EditText etRisk = findViewById(R.id.et_risk_level);
        EditText etCategory = findViewById(R.id.et_category);

        findViewById(R.id.btn_search).setOnClickListener(v -> {
            int stars = Integer.parseInt(etStars.getText().toString());
            String risk = etRisk.getText().toString();
            String cat = etCategory.getText().toString();
            viewModel.searchGames(stars, risk, cat);
        });

        viewModel.getSearchState().observe(this, state -> {
            switch (state.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    adapter.setGames(state.data);
                    if (state.data.isEmpty()) {
                        Toast.makeText(this, "No games found", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error: " + state.errorMessage, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }
}
