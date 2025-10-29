package com.example.attentivnes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Game1 extends AppCompatActivity {
    CountDownTimer timer;
    List<String> players;
    List<String> items_of_items;
    Random random = new Random();
    TextView text11;
    TextView text22;
    TextView countdown;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        items_of_items= new ArrayList<>();
        items_of_items.add("Лампа");
        items_of_items.add("Горшок");
        items_of_items.add("Подушка");
        items_of_items.add("Тарелка");

        mediaPlayer = MediaPlayer.create(this, R.raw.tainstvennaja_muzyka_majnkraft);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.8f, 0.8f);
        mediaPlayer.start();

        players = new ArrayList<>();
        Add_to_list(2);

        text11 = findViewById(R.id.text1);
        text22 = findViewById(R.id.text2);

        text11.setText(players.get(0));
        text22.setText(players.get(1));

        countdown = findViewById(R.id.TextTime);

        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 % 60 > 10)
                {
                    countdown.setText("Времени осталось: " + millisUntilFinished / 1000 / 60 + ":" + millisUntilFinished / 1000 % 60);
                }
                else
                {
                    countdown.setText("Времени осталось: " + millisUntilFinished / 1000 / 60 + ":0" + millisUntilFinished / 1000 % 60);
                }
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
                builder.setTitle("Время вышло!");
                builder.setMessage("Выберите действие:");

                builder.setPositiveButton("Начать заново", (dialog, which) -> {
                    Intent intent = new Intent(Game1.this, Game1.class);
                    startActivity(intent);
                    finish();
                });

                builder.setNegativeButton("Выйти в главное меню", (dialog, which) -> {
                    Intent intent = new Intent(Game1.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }.start();
    }

    public void Add_to_list(int count) {
        for (int i=0; i < count; i++)
        {
            if (items_of_items.size() != 0)
            {
                int x = random.nextInt(items_of_items.size());
                players.add(items_of_items.get(x));
                items_of_items.remove(x);
            }
        }
    }
    public void podushka(View view) {
        check_index("Подушка");
    }

    public void check_index(String name) {
        if (players.contains(name))
        {
            players.remove(name);
            Add_to_list(1);
            if (players.size() == 0)
            {
                text11.setText("");
                win();
            }
            else
            {
                text11.setText(players.get(0));
            }
            if (players.size() <= 1)
            {
                text22.setText("");
            }
            else
            {
                text22.setText(players.get(1));
            }
            Log.d("Чекнул", "sda");
        }
        else
        {
            Log.d("Не чек", "asd");
        }
    }

    public void win()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
        builder.setTitle("Вы выиграли!");
        builder.setMessage("Выберите действие:");

        builder.setPositiveButton("Начать заново", (dialog, which) -> {
            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("Выйти в главное меню", (dialog, which) -> {
            Intent intent = new Intent(Game1.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void gorshok(View view) {
        check_index("Горшок");
    }

    public void lampa(View view) {
        check_index("Лампа");
    }

    public void tarelka(View view) {
        check_index("Тарелка");
    }
}