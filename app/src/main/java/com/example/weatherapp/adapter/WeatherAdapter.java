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
import com.example.weatherapp.entities.WeatherDate;

import java.util.ArrayList;

public class WeatherAdapter extends ArrayAdapter<WeatherDate> {
    public WeatherAdapter(@NonNull Context context, ArrayList<WeatherDate> weatherDateArrayList) {
        super(context, 0, weatherDateArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WeatherDate weatherDate = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_list_view, parent, false);
        }

        TextView dateTextView = convertView.findViewById(R.id.weatherDay);
        TextView maxTextView = convertView.findViewById(R.id.weatherMaxTemperature);
        TextView minTextView = convertView.findViewById(R.id.weatherMinTemperature);

        dateTextView.setText(weatherDate.getDate());
        maxTextView.setText(weatherDate.getMaxTemp());
        minTextView.setText(weatherDate.getMinTemp());

        return convertView;
    }
}
