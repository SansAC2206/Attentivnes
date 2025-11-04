package com.example.attentivnes;

import static com.example.attentivnes.MainActivity.mediaPlayer;

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

        mediaPlayer.start();
        if (mediaPlayer.isPlaying() == false)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.bad_piggies_theme);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.8f, 0.8f);
            mediaPlayer.start();
        }
    }

    public void instruction(View view) {
    }

    public void ease(View view) {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
       Intent intent = new Intent(this, Game1.class);
       startActivity(intent);
       finish();
    }

    public void normal(View view) {
    }

    public void hard(View view) {
    }

    public void back(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    protected void onDestroy() {
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        super.onDestroy();
//    }

//    @Override
//    protected void onStop() {
//        mediaPlayer.stop();
//        super.onStop();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer = MediaPlayer.create(this, R.raw.bad_piggies_theme);
        mediaPlayer.start();
    }
}