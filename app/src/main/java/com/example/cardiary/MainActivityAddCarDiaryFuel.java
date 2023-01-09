package com.example.cardiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.number.Precision;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cardiary.model.CarDiaryDbHelper;
import com.example.cardiary.model.CarDiaryFuel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


public class MainActivityAddCarDiaryFuel extends AppCompatActivity {

    SQLiteDatabase database;
    CarDiaryDbHelper dbHelper;
    EditText fuelLoaded;
    EditText totalPrice;
    EditText pricePerLiter;
    EditText discount;
    EditText sinceLastTimeKm;
    EditText totalKm;
    EditText id;
    EditText idDel;
    CalendarView createDateCalendar;
    Button delete_car_diary_fuel_button;
    long createDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_diary_fuel_add);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fuelLoaded = findViewById(R.id.edit_car_diary_fuel_loaded_fuel);
        totalPrice = findViewById(R.id.edit_car_diary_fuel_totalPrice);
        pricePerLiter = findViewById(R.id.edit_car_diary_price_per_liter);
        discount = findViewById(R.id.edit_car_diary_fuel_discount);
        sinceLastTimeKm = findViewById(R.id.edit_car_diary_fuel_since_last_time_km);
        totalKm = findViewById(R.id.edit_car_diary_fuel_total_Km);
        createDateCalendar = findViewById(R.id.edit_car_diary_fuel_create_date);
        delete_car_diary_fuel_button = findViewById(R.id.delete_car_diary_fuel_bttn);
        id = findViewById(R.id.edit_car_diary_fuel_id);


        createDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                createDate = new Date(year-1900,month,dayOfMonth).getTime();
            }
        });

        if (bundle!=null){
            if (intent.getStringExtra("id") != null) {
                dbHelper = new CarDiaryDbHelper(this);
                // отваряне БД
                database = dbHelper.getWritableDatabase();
                String query = "SELECT * FROM " + dbHelper.CAR_DIARY_FUEL_TABLE + " WHERE id = " + intent.getStringExtra("id") + " LIMIT 1";
                Cursor cursor = database.rawQuery(query, null);


                while (cursor.moveToNext()) {
                    id.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
                    fuelLoaded.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("loadedFuel"))));
                    pricePerLiter.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("pricePerLiter"))));
                    discount.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("discount"))));
                    totalPrice.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("totalPrice"))));
                    sinceLastTimeKm.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("scineLastTimeKm"))));
                    totalKm.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("totalKm"))));
                    createDateCalendar.setDate(cursor.getLong(7));
                }
                cursor.close();
                delete_car_diary_fuel_button.setVisibility(View.VISIBLE);
            }
        }

        //Пресмята обща цена след промяна на заредено гориво, остъпка и цена на литър
        fuelLoaded.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    sumTotalSum();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (count>0){
                    sumTotalSum();
                }

            }
        });

        pricePerLiter.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    sumTotalSum();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (count>0){
                    sumTotalSum();
                }
            }
        });

        discount.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    sumTotalSum();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (count>0){
                    sumTotalSum();
                }
            }
        });
    }

    protected void sumTotalSum(){
        double totalPriceSum = Double.valueOf(String.valueOf(fuelLoaded.getText())) * Double.valueOf(String.valueOf(pricePerLiter.getText())) - Double.valueOf(String.valueOf(discount.getText()));

        if (totalPriceSum>0){
            double scale = Math.pow(10, 2);
            totalPriceSum = Math.round(totalPriceSum * scale) / scale;
            totalPrice.setText(String.valueOf(totalPriceSum));
        }
        else {
            totalPrice.setText(String.valueOf(totalPriceSum));
        }

    }

    public void saveCarDiaryFuel(View view) {
        dbHelper = new CarDiaryDbHelper(this);
        // отваряне БД
        database = dbHelper.getWritableDatabase();

        if (TextUtils.isEmpty(fuelLoaded.getText().toString()) ||
                TextUtils.isEmpty(pricePerLiter.getText().toString()) ||
                TextUtils.isEmpty(totalPrice.getText().toString()) ||
                TextUtils.isEmpty(sinceLastTimeKm.getText().toString()) ||
                TextUtils.isEmpty(totalKm.getText().toString())
        ){
            Toast.makeText(this, getText(R.string.please_enter_all_fields), Toast.LENGTH_LONG).show();
            return;
        }

        //Записва в базата запис от дневник на горивото
        ContentValues values = new ContentValues();
        values.put("loadedFuel", Float.valueOf(String.valueOf(fuelLoaded.getText())));
        values.put("pricePerLiter", Float.valueOf(String.valueOf(pricePerLiter.getText())));
        values.put("discount", Float.valueOf(String.valueOf(discount.getText())));
        values.put("totalPrice", Float.valueOf(String.valueOf(totalPrice.getText())));
        values.put("scineLastTimeKm", Float.valueOf(String.valueOf(sinceLastTimeKm.getText())));
        values.put("totalKm", Float.valueOf(String.valueOf(totalKm.getText())));

        if(!String.valueOf(id.getText()).isEmpty()){
            if (createDate != 0) {
                values.put("createDate", createDate);
            }
            database.update(dbHelper.CAR_DIARY_FUEL_TABLE, values,"id = "+Integer.valueOf(String.valueOf(id.getText())),null);
        }
        else {
            if (createDate != 0) {
                values.put("createDate", createDate);
            }
            else {
                values.put("createDate", new Date().getTime());
            }
            database.insert(dbHelper.CAR_DIARY_FUEL_TABLE, null, values);
        }


        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void closeCarDiaryFuel(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void deleteCarDiaryFuel(View view) {

        dbHelper = new CarDiaryDbHelper(this);
        // отваряне БД
        database = dbHelper.getWritableDatabase();
        idDel = id;
        Intent intentDelete = new Intent(this,MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.are_you_sure_delete_this_item)+"?");
        builder.setTitle(getText(R.string.deleteItem)+"!");
        builder.setCancelable(false);

        builder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {

                // Изтриване на запис
                database.delete(dbHelper.CAR_DIARY_FUEL_TABLE,"id = "+String.valueOf(idDel.getText()),null);
                dialogInterface.cancel();
                startActivity(intentDelete);
            }
        });
        builder.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onDestroy() {
        // затваряме връзката с БД
        if (database!=null){
            database.close();
        }
        if (dbHelper!=null){
            dbHelper.close();
        }
        super.onDestroy();
    }


}
