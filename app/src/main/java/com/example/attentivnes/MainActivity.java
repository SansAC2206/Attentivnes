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
    static MediaPlayer mediaPlayer;
    private static boolean isAppInForeground = true;
    public static boolean isExiting = false;

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

        isExiting = false;

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bad_piggies_theme);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.8f, 0.8f);
        }

        if (!mediaPlayer.isPlaying() && isAppInForeground && !isExiting) {
            mediaPlayer.start();
        }
    }

    public void start(View view) {
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
    }

    public void exit(View view) {
        isExiting = true;

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isAppInForeground = false;

        finishAffinity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void settings(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppInForeground = true;
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isExiting) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isChangingConfigurations() && mediaPlayer != null && mediaPlayer.isPlaying() && !isExiting) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((isFinishing() || isExiting) && mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit(null);
    }
}