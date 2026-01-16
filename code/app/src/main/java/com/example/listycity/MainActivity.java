package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    Button btnAdd, btnDelete;

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    int selectedPosition = -1;

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

        // 1. Find views
        cityList = findViewById(R.id.city_list);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);

        // 2. Data setup
        String[] cities = {
                "Edmonton", "Vancouver", "Moscow", "Sydney",
                "Berlin", "Vienna", "Tokyo", "Beijing",
                "Osaka", "New Delhi"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // 3. List item click (select city)
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        // 4. Delete button
        btnDelete.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1;
            }
        });

        // 5. Add button (dialog)
        btnAdd.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Add City");

            View dialogView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_add_city, null);
            EditText editCity = dialogView.findViewById(R.id.edit_city);

            builder.setView(dialogView);

            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String cityName = editCity.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("CANCEL", null);
            builder.show();
        });
    }
}
