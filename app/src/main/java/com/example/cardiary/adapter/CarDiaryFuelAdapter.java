package com.example.cardiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.cardiary.R;
import com.example.cardiary.model.CarDiaryFuel;

import java.util.ArrayList;

public class CarDiaryFuelAdapter extends ArrayAdapter<CarDiaryFuel> {

    public CarDiaryFuelAdapter(Context context, ArrayList<CarDiaryFuel> carDiaryFuels) {
        super(context, 0, carDiaryFuels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.car_diary_fuel_list_items, parent, false);
        }
        CarDiaryFuel currentSite = getItem(position);




        TextView car_diary_fuel_loaded_fuel = listItemView.findViewById(R.id.car_diary_fuel_loaded_fuel);
        car_diary_fuel_loaded_fuel.setText(( String.valueOf( currentSite.getLoadedFuel()) +" л."));

        TextView car_diary_fuel_price_per_liter = listItemView.findViewById(R.id.car_diary_fuel_price_per_liter);
        car_diary_fuel_price_per_liter.setText(String.valueOf(String.format("%.2f", currentSite.getPricePerLiter()))+ " лв.");

        TextView car_diary_fuel_discount = listItemView.findViewById(R.id.car_diary_fuel_discount);
        car_diary_fuel_discount.setText(String.valueOf(String.format("%.2f", currentSite.getDiscount()))+ " лв.");

        TextView car_diary_fuel_totalPrice = listItemView.findViewById(R.id.car_diary_fuel_totalPrice);
        car_diary_fuel_totalPrice.setText(String.valueOf(String.format("%.2f", currentSite.getTotalPrice()))+ " лв.");

        TextView car_diary_fuel_science_last_time_km = listItemView.findViewById(R.id.car_diary_fuel_since_last_time_km);
        car_diary_fuel_science_last_time_km.setText(String.valueOf(currentSite.getScienceLastTimeKm())+ " км.");

        TextView car_diary_fuel_total_Km = listItemView.findViewById(R.id.car_diary_fuel_total_Km);
        car_diary_fuel_total_Km.setText(String.valueOf(currentSite.getTotalKm())+ " км.");

        TextView car_diary_fuel_create_date = listItemView.findViewById(R.id.car_diary_fuel_create_date);
        car_diary_fuel_create_date.setText(String.valueOf(currentSite.getFormatDate("d.M.Y")));

        //View textContainer = listItemView.findViewById(R.id.text_container);
        //int color = ContextCompat.getColor(getContext(), mColorResourceId);
       // textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
