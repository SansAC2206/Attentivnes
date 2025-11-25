package com.example.attentivnes;

import static com.example.attentivnes.MainActivity.mediaPlayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
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
import static com.example.attentivnes.MainActivity.isExiting;

public class Game1 extends AppCompatActivity {
    CountDownTimer timer;
    List<String> players;
    List<String> items_of_items;
    Random random = new Random();
    TextView text11;
    TextView text22;
    TextView countdown;
    MediaPlayer mediaSound;
    MediaPlayer gameMusic;

    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private FrameLayout zoomContainer;
    private View staticUI;
    private float posX = 0f, posY = 0f;
    private float maxPosX, maxPosY, minPosX, minPosY;

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

        zoomContainer = findViewById(R.id.zoomContainer);
        staticUI = findViewById(R.id.staticUI);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        items_of_items = new ArrayList<>();
        items_of_items.add("Лампа");
        items_of_items.add("Горшок");
        items_of_items.add("Подушка");
        items_of_items.add("Тарелка");

        gameMusic = MediaPlayer.create(this, R.raw.tainstvennaja_muzyka_majnkraft);
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.8f, 0.8f);
        gameMusic.start();

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
                if (millisUntilFinished / 1000 % 60 >= 10) {
                    countdown.setText("Времени осталось: " + millisUntilFinished / 1000 / 60 + ":" + millisUntilFinished / 1000 % 60);
                } else {
                    countdown.setText("Времени осталось: " + millisUntilFinished / 1000 / 60 + ":0" + millisUntilFinished / 1000 % 60);
                }
            }

            @Override
            public void onFinish() {
                gameMusic.stop();
                mediaSound = MediaPlayer.create(Game1.this, R.raw.fail);
                mediaSound.setVolume(0.8f, 0.8f);
                mediaSound.setLooping(false);
                mediaSound.start();
                AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
                builder.setTitle("Время вышло!");
                builder.setMessage("Выберите действие:");

                builder.setPositiveButton("Начать заново", (dialog, which) -> {
                    Intent intent = new Intent(Game1.this, Game1.class);
                    startActivity(intent);
                    finish();
                });

                builder.setNegativeButton("Выйти в меню выбора сложности", (dialog, which) -> {
                    Intent intent = new Intent(Game1.this, Difficulty.class);
                    startActivity(intent);
                    finish();
                });

                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }.start();

        zoomContainer.post(new Runnable() {
            @Override
            public void run() {
                calculateBounds();
            }
        });

        zoomContainer.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY;
            private boolean isScaling = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX() - posX;
                        startY = event.getY() - posY;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (!scaleGestureDetector.isInProgress() && !isScaling) {
                            float newPosX = event.getX() - startX;
                            float newPosY = event.getY() - startY;

                            newPosX = Math.max(minPosX, Math.min(maxPosX, newPosX));
                            newPosY = Math.max(minPosY, Math.min(maxPosY, newPosY));

                            posX = newPosX;
                            posY = newPosY;

                            applyZoomAndPan();
                        }
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        isScaling = true;
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        isScaling = false;
                        break;
                }
                return true;
            }
        });
    }

    private void calculateBounds() {
        float containerWidth = zoomContainer.getWidth();
        float containerHeight = zoomContainer.getHeight();

        maxPosX = (containerWidth * (scaleFactor - 1)) / 2;
        maxPosY = (containerHeight * (scaleFactor - 1)) / 2;
        minPosX = -maxPosX;
        minPosY = -maxPosY;

        posX = Math.max(minPosX, Math.min(maxPosX, posX));
        posY = Math.max(minPosY, Math.min(maxPosY, posY));
        applyZoomAndPan();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float previousScale = scaleFactor;
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 3.0f));

            float scaleChange = scaleFactor / previousScale;
            posX *= scaleChange;
            posY *= scaleChange;

            calculateBounds();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            calculateBounds();
        }
    }

    private void applyZoomAndPan() {
        zoomContainer.setScaleX(scaleFactor);
        zoomContainer.setScaleY(scaleFactor);
        zoomContainer.setTranslationX(posX);
        zoomContainer.setTranslationY(posY);
    }

    public void resetZoom(View view) {
        scaleFactor = 1.0f;
        posX = 0f;
        posY = 0f;
        applyZoomAndPan();
        zoomContainer.post(new Runnable() {
            @Override
            public void run() {
                calculateBounds();
            }
        });
    }

    public void Add_to_list(int count) {
        for (int i = 0; i < count; i++) {
            if (items_of_items.size() != 0) {
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
        if (players.contains(name)) {
            players.remove(name);
            Add_to_list(1);
            mediaSound = MediaPlayer.create(Game1.this, R.raw.zvuk_pravilnogo_otveta_5fsd5);
            mediaSound.setVolume(0.8f, 0.8f);
            mediaSound.setLooping(false);
            mediaSound.start();
            if (players.size() == 0) {
                text11.setText("");
                win();
            } else {
                text11.setText(players.get(0));
            }
            if (players.size() <= 1) {
                text22.setText("");
            } else {
                text22.setText(players.get(1));
            }
            Log.d("Чекнул", "sda");
        } else {
            Log.d("Не чек", "asd");
        }
    }

    public void win() {
        timer.cancel();
        gameMusic.stop();
        mediaSound = MediaPlayer.create(Game1.this, R.raw.ura_win);
        mediaSound.setVolume(0.1f, 0.1f);
        mediaSound.setLooping(false);
        mediaSound.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
        builder.setTitle("Вы выиграли!");
        builder.setMessage("Выберите действие:");

        builder.setPositiveButton("Начать заново", (dialog, which) -> {
            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("Выйти в меню выбора сложности", (dialog, which) -> {
            Intent intent = new Intent(Game1.this, Difficulty.class);
            startActivity(intent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
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

    public void back(View view) {
        timer.cancel();
        gameMusic.stop();
        if (MainActivity.mediaPlayer != null && !MainActivity.mediaPlayer.isPlaying()) {
            MainActivity.mediaPlayer.start();
        }
        Intent intent = new Intent(Game1.this, Difficulty.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameMusic != null && gameMusic.isPlaying() && !isExiting) {
            gameMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameMusic != null && !gameMusic.isPlaying() && !isExiting) {
            gameMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameMusic != null && !isExiting) {
            gameMusic.stop();
            gameMusic.release();
            gameMusic = null;
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back(null);
    }
}