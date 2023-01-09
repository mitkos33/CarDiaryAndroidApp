package com.example.cardiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cardiary.R;
import com.example.cardiary.model.CarDiaryFuel;
import com.example.cardiary.model.CarDiaryRepairs;

import java.util.ArrayList;

public class CarDiaryRepairsAdapter extends ArrayAdapter <CarDiaryRepairs> {

    public CarDiaryRepairsAdapter(Context context, ArrayList<CarDiaryRepairs> carDiaryRepairs) {
        super(context, 0, carDiaryRepairs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.car_diary_repairs_list_item, parent, false);
        }
        CarDiaryRepairs currentSite = getItem(position);


//        TextView car_diary_fuel_loaded_id = listItemView.findViewById(R.id.car_diary_fuel_loaded_id);
//        car_diary_fuel_loaded_id.setText(( String.valueOf( currentSite.getId())));


        TextView car_diary_repairs_name = listItemView.findViewById(R.id.car_diary_repairs_name);
        car_diary_repairs_name.setText(( String.valueOf( currentSite.getNameRepair())));

        TextView car_diary_repairs_description = listItemView.findViewById(R.id.car_diary_repairs_description);
        car_diary_repairs_description.setText(( String.valueOf( currentSite.getDescription())));

        TextView car_diary_repairs_create_date = listItemView.findViewById(R.id.car_diary_repairs_create_date);
        car_diary_repairs_create_date.setText(String.valueOf(currentSite.getFormatDate("d.M.Y")));



        return listItemView;
    }
}
