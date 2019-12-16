package com.example.weatherapp.entities;

import java.io.Serializable;

public class MyMarker implements Serializable {

    private String markerKey;
    private double markerLatitude;
    private double markerLongitude;

    public MyMarker() {
    }

    public MyMarker(double markerLatitude, double markerLongitude) {
        this.markerLatitude = markerLatitude;
        this.markerLongitude = markerLongitude;
    }

    public String getMarkerKey() {
        return markerKey;
    }

    public void setMarkerKey(String markerKey) {
        this.markerKey = markerKey;
    }

    public double getMarkerLatitude() {
        return markerLatitude;
    }

    public void setMarkerLatitude(double markerLatitude) {
        this.markerLatitude = markerLatitude;
    }

    public double getMarkerLongitude() {
        return markerLongitude;
    }

    public void setMarkerLongitude(double markerLongitude) {
        this.markerLongitude = markerLongitude;
    }
}
