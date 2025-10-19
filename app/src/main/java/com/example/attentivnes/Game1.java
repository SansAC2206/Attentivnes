package com.example.attentivnes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attentivnes.Models.HorizontalSpaceItemDecoration;
import com.example.attentivnes.Models.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game1 extends AppCompatActivity {
    RecyclerView playersRecyclerView;
    ItemsAdapter adapter;
    List<String> players;
    List<String> items_of_items;
    Random random = new Random();
    TextView text11;
    TextView text22;
    int space = 0;

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

//        playersRecyclerView = findViewById(R.id.playersRecyclerView);

        items_of_items= new ArrayList<>();
        items_of_items.add("Лампа");
        items_of_items.add("Горшок");
        items_of_items.add("Подушка");
        items_of_items.add("Тарелка");

        players = new ArrayList<>();
        Add_to_list(2);

        text11 = findViewById(R.id.text1);
        text22 = findViewById(R.id.text2);

        text11.setText(players.get(0));
        text22.setText(players.get(1));

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        playersRecyclerView.setLayoutManager(layoutManager);

        adapter = new ItemsAdapter(players);
//        playersRecyclerView.setAdapter(adapter);
//        playersRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(space));
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
            //playersRecyclerView.invalidate();
            Log.d("Чекнул", "sda");
        }
        else
        {
            Log.d("Не чек", "asd");
        }
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