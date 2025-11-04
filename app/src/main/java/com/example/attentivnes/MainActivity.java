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
    static MediaPlayer mediaPlayer;

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
        if (mediaPlayer.isPlaying() == false)
        {
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.8f, 0.8f);
            mediaPlayer.start();
        }
    }

    public void start(View view) {
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void settings(View view) {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}