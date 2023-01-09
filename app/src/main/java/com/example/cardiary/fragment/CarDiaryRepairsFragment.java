package com.example.cardiary.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.cardiary.MainActivityAddCarDiaryFuel;
import com.example.cardiary.MainActivityAddCarDiaryRepairs;
import com.example.cardiary.adapter.CarDiaryFuelAdapter;
import com.example.cardiary.R;
import com.example.cardiary.adapter.CarDiaryRepairsAdapter;
import com.example.cardiary.model.CarDiaryDbHelper;
import com.example.cardiary.model.CarDiaryFuel;
import com.example.cardiary.model.CarDiaryRepairs;

import java.util.ArrayList;
import java.util.Date;

public class CarDiaryRepairsFragment extends Fragment {

    SQLiteDatabase database;
    CarDiaryDbHelper dbHelper;
    public CarDiaryRepairsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.car_diary_repairs_list, container, false);
        dbHelper = new CarDiaryDbHelper(this.getContext());
        // отваряне БД
        database = dbHelper.getWritableDatabase();


        String query = "SELECT * FROM "+dbHelper.CAR_DIARY_REPAIRS_TABLE + " ORDER BY createDate desc";
        Cursor cursor = database.rawQuery(query, null);
        // Създаване на списък с дневник за ремонтите
        final ArrayList<CarDiaryRepairs> carDiaryRepairsList = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("nameRepair"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            long createDate = cursor.getLong(cursor.getColumnIndexOrThrow("createDate"));
            carDiaryRepairsList.add(new CarDiaryRepairs(id,name,description,new Date(createDate)));
        }
        cursor.close();
        CarDiaryRepairsAdapter adapter = new CarDiaryRepairsAdapter(getActivity(), carDiaryRepairsList);
        final ListView listView = rootview.findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button createCarDiaryRepairsItem = (Button) rootview.findViewById(R.id.createCarDiaryRepairsItem);
        createCarDiaryRepairsItem.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MainActivityAddCarDiaryRepairs.class);
                startActivity(intent);
            }

        });

        //Редактиране на ред от списъка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CarDiaryRepairs carDiaryRepair = carDiaryRepairsList.get(position);
                Intent intent = new Intent(getActivity(), MainActivityAddCarDiaryRepairs.class);
                intent.putExtra("id", String.valueOf(carDiaryRepair.getId()));
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
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