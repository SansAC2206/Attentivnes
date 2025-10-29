package com.example.attentivnes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startBtn = findViewById(R.id.game);

        mediaPlayer = MediaPlayer.create(this, R.raw.bad_piggies_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.8f, 0.8f);
        mediaPlayer.start();
    }

    public void start(View view) {
        Intent intent = new Intent(this, Game1.class);
        startActivity(intent);
        finish();
        mediaPlayer.stop();
    }
}