package com.example.cardiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardiary.model.CarDiaryDbHelper;

import java.util.Date;

public class MainActivityAddCarDiaryRepairs extends AppCompatActivity {

    SQLiteDatabase database;
    CarDiaryDbHelper dbHelper;
    EditText id;
    EditText name;
    EditText description;
    EditText idDel;
    CalendarView createDateCalendar;
    Button delete_car_diary_repairs_bttn;
    long createDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_diary_repairs_add);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = findViewById(R.id.edit_car_diary_repairs_id);
        name = findViewById(R.id.edit_car_diary_repairs_name);
        description = findViewById(R.id.edit_car_diary_repairs_description);
        createDateCalendar = findViewById(R.id.edit_car_diary_repairs_create_date);
        delete_car_diary_repairs_bttn = findViewById(R.id.delete_car_diary_repairs_bttn);

        if (bundle!=null){
            dbHelper = new CarDiaryDbHelper(this);
            // отваряне БД
            database = dbHelper.getWritableDatabase();

            if (intent.getStringExtra("id") != null) {

                String query = "SELECT * FROM " + dbHelper.CAR_DIARY_REPAIRS_TABLE + " WHERE id = " + intent.getStringExtra("id") + " LIMIT 1";
                Cursor cursor = database.rawQuery(query, null);


                while (cursor.moveToNext()) {
                    id.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
                    name.setText(cursor.getString(cursor.getColumnIndexOrThrow("nameRepair")));
                    description.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                    createDateCalendar.setDate(cursor.getLong(3));
                }
                cursor.close();
                delete_car_diary_repairs_bttn.setVisibility(View.VISIBLE);
            }
        }
        createDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                createDate = new Date(year-1900,month,dayOfMonth).getTime();
            }
        });
    }

    public void saveCarDiaryRepairs(View view) {
        dbHelper = new CarDiaryDbHelper(this);
        // отваряне БД
        database = dbHelper.getWritableDatabase();

        if (TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(description.getText().toString())
        ){
            Toast.makeText(this, getText(R.string.please_enter_all_fields), Toast.LENGTH_LONG).show();
            return;
        }

        //Записва в базата запис от дневник на горивото
        ContentValues values = new ContentValues();
        values.put("nameRepair", String.valueOf(name.getText()) );
        values.put("description", String.valueOf(description.getText()));


        if(!String.valueOf(id.getText()).isEmpty()){
            if (createDate != 0) {
                values.put("createDate", createDate);
            }
            database.update(dbHelper.CAR_DIARY_REPAIRS_TABLE, values,"id = "+Integer.valueOf(String.valueOf(id.getText())),null);
        }
        else {
            if (createDate != 0) {
                values.put("createDate", createDate);
            }
            else {
                values.put("createDate", new Date().getTime());
            }
            database.insert(dbHelper.CAR_DIARY_REPAIRS_TABLE, null, values);
        }


        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("isRepairs", "1");
        startActivity(intent);
    }

    public void deleteCarDiaryRepairs(View view) {

        dbHelper = new CarDiaryDbHelper(this);
        // отваряне БД
        database = dbHelper.getWritableDatabase();
        idDel = id;
        Intent intentDelete = new Intent(this,MainActivity.class);
        intentDelete.putExtra("isRepairs", "1");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.are_you_sure_delete_this_item)+"?");
        builder.setTitle(getText(R.string.deleteItem)+"!");
        builder.setCancelable(false);

        builder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {

                // Изтриване на запис
                database.delete(dbHelper.CAR_DIARY_REPAIRS_TABLE,"id = "+String.valueOf(idDel.getText()),null);
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

    public void closeCarDiaryRepairs(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("isRepairs", "1");
        startActivity(intent);
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