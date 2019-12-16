package com.example.weatherapp.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weatherapp.entities.MyMarker;
import com.example.weatherapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button randomButton;
    private Button deleteButton;
    private Button historyButton;
    private Map<Integer, Marker> googleMarkerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        randomButton = findViewById(R.id.randomButton);
        View.OnClickListener randomOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!googleMarkerMap.isEmpty()) {
                    googleMarkerMap.clear();
                    mMap.clear();
                }

                for (int i = 1; i <= 10; i++) {
                    double lat = (Math.random() - 0.5) * 2 * 90;
                    double lng = (Math.random() - 0.5) * 2 * 90;
                    LatLng geoPosition = new LatLng(lat, lng);

                    String formattedLatitude = String.format("%.2f", geoPosition.latitude);
                    String formattedLongitude = String.format("%.2f", geoPosition.longitude);
                    String markerTitle = "Latitude: " + formattedLatitude + "\n" + "Longitude: " + formattedLongitude;

                    Marker googleMarker = mMap.addMarker(new MarkerOptions()
                            .position(geoPosition)
                            .title("Marker - " + i)
                            .snippet(markerTitle)
                            .flat(true)
                            .draggable(true));
                    googleMarkerMap.put(i, googleMarker);
                }
            }
        };
        randomButton.setOnClickListener(randomOnClickListener);

        deleteButton = findViewById(R.id.deleteButton);
        View.OnClickListener deleteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMarkerMap.clear();
                mMap.clear();
            }
        };
        deleteButton.setOnClickListener(deleteClickListener);

        historyButton = findViewById(R.id.historyButton);
        View.OnClickListener newPageOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, WeatherHistoryActivity.class);
                startActivity(intent);
            }
        };
        historyButton.setOnClickListener(newPageOnClickListener);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                MyMarker geoMarker = new MyMarker(marker.getPosition().latitude, marker.getPosition().longitude);
                Intent intent = new Intent(MapsActivity.this, EmptyActivity.class);

                if (geoMarker != null) {
                    intent.putExtra(MyMarker.class.getSimpleName(), geoMarker);
                }
                startActivity(intent);
                return true;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                String formattedLatitude = String.format("%.2f", latLng.latitude);
                String formattedLongitude = String.format("%.2f", latLng.longitude);
                String markerTitle = "Latitude: " + formattedLatitude + "\n" + "Longitude: " + formattedLongitude;

                Marker googleMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker - " + (googleMarkerMap.size() + 1))
                        .snippet(markerTitle)
                        .flat(true)
                        .draggable(true));

                googleMarkerMap.put(googleMarkerMap.size() + 1, googleMarker);
            }
        });

    }
}
