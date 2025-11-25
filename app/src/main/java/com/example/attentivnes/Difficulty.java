package com.example.attentivnes;

import static com.example.attentivnes.MainActivity.mediaPlayer;
import static com.example.attentivnes.MainActivity.isExiting;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Difficulty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_difficulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isExiting) {
            mediaPlayer.start();
        }
    }

    public void instruction(View view) {
    }

    public void ease(View view) {
        Intent intent = new Intent(this, Game1.class);
        startActivity(intent);
        finish();
    }

    public void normal(View view) {
    }

    public void hard(View view) {
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isChangingConfigurations() && mediaPlayer != null && mediaPlayer.isPlaying() && !isExiting) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isExiting) {
            mediaPlayer.start();
        }
    }
}