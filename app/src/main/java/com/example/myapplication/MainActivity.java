package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.ui.PlayActivity;
import com.example.myapplication.ui.RateActivity;
import com.example.myapplication.ui.SearchActivity;
import com.example.myapplication.ui.AddBalanceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.button_click);

        View cardSearch = findViewById(R.id.card_search);
        cardSearch.setOnClickListener(v -> {
            v.startAnimation(scaleAnim);
            startActivity(new Intent(this, SearchActivity.class));
        });

        View cardPlay = findViewById(R.id.card_play);
        cardPlay.setOnClickListener(v -> {
            v.startAnimation(scaleAnim);
            startActivity(new Intent(this, PlayActivity.class));
        });

        View cardRate = findViewById(R.id.card_rate);
        cardRate.setOnClickListener(v -> {
            v.startAnimation(scaleAnim);
            startActivity(new Intent(this, RateActivity.class));
        });

        View cardBalance = findViewById(R.id.card_balance);
        cardBalance.setOnClickListener(v -> {
            v.startAnimation(scaleAnim);
            startActivity(new Intent(this, AddBalanceActivity.class));
        });
    }
}
