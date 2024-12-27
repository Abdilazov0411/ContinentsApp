package com.example.continentsapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private final HashMap<String, HashMap<String, List<String>>> data = new HashMap<>();
    private List<String> currentList = new ArrayList<>();
    private String currentLevel = "continents";
    private String selectedContinent = "";
    private String selectedCountry = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeData();

        currentList = new ArrayList<>(data.keySet());
        adapter = new MyAdapter(currentList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }

    private void initializeData() {
        HashMap<String, List<String>> europe = new HashMap<>();
        europe.put("France", List.of("Paris", "Lyon", "Marseille"));
        europe.put("Germany", List.of("Berlin", "Munich", "Hamburg"));
        europe.put("Italy", List.of("Rome", "Milan", "Naples"));

        HashMap<String, List<String>> asia = new HashMap<>();
        asia.put("China", List.of("Beijing", "Shanghai", "Shenzhen"));
        asia.put("Japan", List.of("Tokyo", "Osaka", "Kyoto"));
        asia.put("India", List.of("Delhi", "Mumbai", "Bangalore"));

        data.put("Europe", europe);
        data.put("Asia", asia);
    }

    private void onItemClick(String item) {
        switch (currentLevel) {
            case "continents":
                selectedContinent = item;
                currentLevel = "countries";
                currentList = new ArrayList<>(data.get(item).keySet());
                adapter.updateList(currentList);
                break;
            case "countries":
                selectedCountry = item;
                currentLevel = "cities";
                currentList = new ArrayList<>(data.get(selectedContinent).get(item));
                adapter.updateList(currentList);
                break;
            case "cities":
                Toast.makeText(this, "Selected city: " + item, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentLevel.equals("cities")) {
            currentLevel = "countries";
            currentList = new ArrayList<>(data.get(selectedContinent).keySet());
        } else if (currentLevel.equals("countries")) {
            currentLevel = "continents";
            currentList = new ArrayList<>(data.keySet());
        } else {
            super.onBackPressed();
            return;
        }
        adapter.updateList(currentList);
    }
}