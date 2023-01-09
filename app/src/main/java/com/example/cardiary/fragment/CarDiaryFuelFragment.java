package com.example.cardiary.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cardiary.adapter.CarDiaryFuelAdapter;
import com.example.cardiary.MainActivityAddCarDiaryFuel;
import com.example.cardiary.R;
import com.example.cardiary.model.CarDiaryDbHelper;
import com.example.cardiary.model.CarDiaryFuel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CarDiaryFuelFragment extends Fragment {
    SQLiteDatabase database;
    CarDiaryDbHelper dbHelper;
    public CarDiaryFuelFragment(){

    }
    TextView id ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.car_diary_fuel_list, container, false);

        dbHelper = new CarDiaryDbHelper(this.getContext());
        // отваряне БД
        database = dbHelper.getWritableDatabase();

        //Заявка за извличане на данни от базата
        String query = "SELECT * FROM "+dbHelper.CAR_DIARY_FUEL_TABLE + " ORDER BY createDate desc";
        Cursor cursor = database.rawQuery(query, null);

        // Създаване на списък с дневник за горивото
        final ArrayList<CarDiaryFuel> carDiaryFuels = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            float loadedFuel = cursor.getFloat(cursor.getColumnIndexOrThrow("loadedFuel"));
            BigDecimal pricePerLiter = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("pricePerLiter")));
            BigDecimal discount = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("discount")));
            BigDecimal totalPrice = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("totalPrice")));
            float scienceLastTime = cursor.getFloat(cursor.getColumnIndexOrThrow("scineLastTimeKm"));
            float totalKm = cursor.getFloat(cursor.getColumnIndexOrThrow("totalKm"));
            long createDate = cursor.getLong(cursor.getColumnIndexOrThrow("createDate"));
            carDiaryFuels.add(new CarDiaryFuel(id,loadedFuel, pricePerLiter, discount, totalPrice,scienceLastTime,totalKm, new Date(createDate)));
        }
        cursor.close();



        CarDiaryFuelAdapter adapter = new CarDiaryFuelAdapter(getActivity(), carDiaryFuels);
        final ListView listView = rootview.findViewById(R.id.list);
        listView.setAdapter(adapter);


        Button createCarDiaryFuelItemButton =  rootview.findViewById(R.id.createCarDiaryFuelItem);
        createCarDiaryFuelItemButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MainActivityAddCarDiaryFuel.class);
                startActivity(intent);
            }

        });

        //Редактиране на ред от списъка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CarDiaryFuel carDiaryFuel = carDiaryFuels.get(position);
                Intent intent = new Intent(getActivity(), MainActivityAddCarDiaryFuel.class);
                intent.putExtra("id", String.valueOf(carDiaryFuel.getId()));
                startActivity(intent);
            }
        });

        return rootview;
    }



    @Override
    public void onDestroy() {
        // затваряме връзката с БД
        database.close();
        dbHelper.close();

        super.onDestroy();
    }
}
