package com.example.weatherapp;

import android.net.Uri;
import android.util.Log;

import com.example.weatherapp.entities.MyMarker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String TAG = "NetworkUtils";
    private final static String WEATHER_GEOPOSITION_URL = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
    private final static String WEATHERDB_BASE_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private final static String PARAM_API_KEY = "apikey";
    /*private final static String API_KEY = "a6RfArhKoDmLuTu0bIYQup1OZBSPYgEP";*/
    /*private final static String API_KEY = "Yg84KgEIP9jCDPUzUDUOyL3vwUvlulCN";*/
    private final static String API_KEY = "lfww8T4TaAMDmCWM4vSGUWRbEASkYxWi";
    private final static String PARAM_METRIC = "metric";
    private final static String METRIC_VALUE = "true";
    private final static String PARAM_GEO_KEY = "q";

    public static URL buildUrlForSearchGeo(MyMarker geoMarker) {
        Uri buildUrlSearch = Uri.parse(WEATHER_GEOPOSITION_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_GEO_KEY, geoMarker.getMarkerLatitude() + "," + geoMarker.getMarkerLongitude())
                .build();

        URL geoUrl = null;
        try {
            geoUrl = new URL(buildUrlSearch.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "buildUrlForWeather: geoUrl: " + geoUrl);
        return geoUrl;
    }

    public static URL buildUrlForWeather(MyMarker geoMarker) {
        String weatherUrl = WEATHERDB_BASE_URL + geoMarker.getMarkerKey();
        Uri buildUrl = Uri.parse(weatherUrl).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "buildUrlForWeather: url: " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
