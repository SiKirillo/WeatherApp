package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.HistoryAdapter;
import com.example.weatherapp.entities.HistoryDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WeatherHistoryActivity extends AppCompatActivity {

    private ArrayList<HistoryDate> historyDateArrayList = new ArrayList<>();
    private ListView listView;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_history);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            HistoryDate historyDate = new HistoryDate();
            historyDate = (HistoryDate) arguments.getSerializable(HistoryDate.class.getSimpleName());
            historyDateArrayList.add(historyDateArrayList.size() + 1, historyDate);
        }

        listView = findViewById(R.id.weatherHistoryView);

        if (historyDateArrayList != null) {
            HistoryAdapter historyAdapter = new HistoryAdapter(this, historyDateArrayList);
            listView.setAdapter(historyAdapter);
        }

        backButton = findViewById(R.id.backButton);
        View.OnClickListener backClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(WeatherHistoryActivity.this, MapsActivity.class);
                startActivity(backIntent);
            }
        };
        backButton.setOnClickListener(backClickListener);
    }

}
