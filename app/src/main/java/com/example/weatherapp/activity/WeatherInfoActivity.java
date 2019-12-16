package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.weatherapp.entities.MyMarker;
import com.example.weatherapp.NetworkUtils;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.entities.WeatherDate;
import com.example.weatherapp.entities.HistoryDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherInfoActivity extends AppCompatActivity {

    private static final String TAG = WeatherInfoActivity.class.getSimpleName();
    private ArrayList<WeatherDate> weatherDateArrayList = new ArrayList<>();
    private HistoryDate historyDate = new HistoryDate();
    private ListView listView;
    private MyMarker geoMarker;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            geoMarker = (MyMarker) arguments.getSerializable(MyMarker.class.getSimpleName());
        }

        listView = findViewById(R.id.weatherListView);

        URL weatherUrl = NetworkUtils.buildUrlForWeather(geoMarker);
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onResume: weatherUrl: " + weatherUrl);

        fillHistoryDate(historyDate, geoMarker, weatherDateArrayList);
        Log.i("array", historyDate.getDate() + "");
        Log.i("array", historyDate.getMarkerLatitude() + "");

        Intent intent = new Intent(WeatherInfoActivity.this, WeatherHistoryActivity.class);
        if (historyDate != null) {
            intent.putExtra(HistoryDate.class.getSimpleName(), historyDate);
        }

        backButton = findViewById(R.id.backButton);
        View.OnClickListener backClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(WeatherInfoActivity.this, MapsActivity.class);
                startActivity(backIntent);
            }
        };
        backButton.setOnClickListener(backClickListener);
    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;

            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults);

            if (weatherSearchResults != null) {
                return weatherSearchResults;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if (weatherSearchResults != null && !weatherSearchResults.equals("")) {
                weatherDateArrayList = parseWeatherJSON(weatherSearchResults);
            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    private ArrayList<WeatherDate> parseWeatherJSON(String weatherSearchResults) {
        if (weatherDateArrayList != null) {
            weatherDateArrayList.clear();
        }

        if (weatherSearchResults != null) {
            try {
                JSONObject jsonObject = new JSONObject(weatherSearchResults);
                JSONArray weatherResults = jsonObject.getJSONArray("DailyForecasts");

                for (int i = 0; i < weatherResults.length(); i++) {
                    WeatherDate weatherDate = new WeatherDate();

                    JSONObject resultObj = weatherResults.getJSONObject(i);

                    String date = resultObj.getString("Date");
                    weatherDate.setDate(date);

                    JSONObject temperatureObj = resultObj.getJSONObject("Temperature");

                    String maxTemperature = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weatherDate.setMaxTemp(maxTemperature);

                    String minTemperature = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weatherDate.setMinTemp(minTemperature);

                    weatherDateArrayList.add(weatherDate);
                }

                if (weatherDateArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherDateArrayList);
                    listView.setAdapter(weatherAdapter);
                }

                return weatherDateArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private HistoryDate fillHistoryDate(HistoryDate historyDate, MyMarker geoMarker, ArrayList<WeatherDate> weatherDateArrayList) {
        if (geoMarker != null && !weatherDateArrayList.isEmpty()) {
            historyDate.setMarkerLatitude(geoMarker.getMarkerLatitude());
            historyDate.setMarkerLongitude(geoMarker.getMarkerLongitude());
            historyDate.setDate(weatherDateArrayList.get(0).getDate());
            historyDate.setMaxTemp(weatherDateArrayList.get(0).getMaxTemp());
            historyDate.setMinTemp(weatherDateArrayList.get(0).getMinTemp());
        }
        return historyDate;
    }
}
