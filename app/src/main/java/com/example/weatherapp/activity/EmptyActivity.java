package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.weatherapp.NetworkUtils;
import com.example.weatherapp.entities.MyMarker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class EmptyActivity extends AppCompatActivity {

    private static final String TAG = EmptyActivity.class.getSimpleName();
    private MyMarker geoMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            geoMarker = (MyMarker) arguments.getSerializable(MyMarker.class.getSimpleName());
        }

        URL searchUrl = NetworkUtils.buildUrlForSearchGeo(geoMarker);
        new FetchWeatherDetails().execute(searchUrl);
        Log.i(TAG, "onStart: geoUrl: " + searchUrl);
    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String geoSearchResults = null;

            try {
                geoSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: geoSearchResults: " + geoSearchResults);

            if (geoSearchResults != null) {
                return geoSearchResults;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String geoSearchResults) {
            if (geoSearchResults != null && !geoSearchResults.equals("")) {
                geoMarker = parseGeoJSON(geoSearchResults);
            }

            Intent intent = new Intent(EmptyActivity.this, WeatherInfoActivity.class);
            if (geoMarker != null) {
                intent.putExtra(MyMarker.class.getSimpleName(), geoMarker);
            }
            finish();
            startActivity(intent);

            super.onPostExecute(geoSearchResults);
        }
    }

    private MyMarker parseGeoJSON(String weatherSearchResults) {
        if (weatherSearchResults != null) {
            try {
                JSONObject jsonObject = new JSONObject(weatherSearchResults);
                String keyObj = jsonObject.getString("Key");
                geoMarker.setMarkerKey(keyObj);
                return geoMarker;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
