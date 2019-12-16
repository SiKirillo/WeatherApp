package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.R;
import com.example.weatherapp.entities.HistoryDate;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<HistoryDate> {
    public HistoryAdapter(@NonNull Context context, ArrayList<HistoryDate> historyDateArrayList) {
        super(context, 0, historyDateArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HistoryDate historyDate = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_history_view, parent, false);
        }

        TextView latitudeTextView = convertView.findViewById(R.id.geoLatitude);
        TextView longitudeTextView = convertView.findViewById(R.id.geoLongitude);
        TextView dateTextView = convertView.findViewById(R.id.weatherDay);
        TextView maxTextView = convertView.findViewById(R.id.weatherMaxTemperature);
        TextView minTextView = convertView.findViewById(R.id.weatherMinTemperature);

        latitudeTextView.setText(String.format("%.2f", historyDate.getMarkerLatitude()));
        longitudeTextView.setText(String.format("%.2f", historyDate.getMarkerLongitude()));
        dateTextView.setText(historyDate.getDate());
        maxTextView.setText(historyDate.getMaxTemp());
        minTextView.setText(historyDate.getMinTemp());

        return convertView;
    }
}
