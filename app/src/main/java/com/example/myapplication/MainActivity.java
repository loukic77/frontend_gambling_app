package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.ui.PlayActivity;
import com.example.myapplication.ui.RateActivity;
import com.example.myapplication.ui.SearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.card_search).setOnClickListener(v -> 
            startActivity(new Intent(this, SearchActivity.class)));

        findViewById(R.id.card_play).setOnClickListener(v -> 
            startActivity(new Intent(this, PlayActivity.class)));

        findViewById(R.id.card_rate).setOnClickListener(v -> 
            startActivity(new Intent(this, RateActivity.class)));
    }
}
