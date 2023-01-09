package com.example.cardiary.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDiaryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "car_diary.db";
    private static final int DATABASE_VERSION = 1;

    public CarDiaryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CAR_DIARY_FUEL_TABLE = "CarDiaryFuel";

    public static final String CAR_DIARY_REPAIRS_TABLE = "carDiaryRepairs";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE  "+CAR_DIARY_FUEL_TABLE+" (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "loadedFuel      FLOAT,\n" +
                "pricePerLiter   NUMBER,\n" +
                "discount        NUMBER,\n" +
                "totalPrice      NUMBER,\n" +
                "scineLastTimeKm FLOAT,\n" +
                "totalKm         FLOAT,\n" +
                "createDate      DATE);";
        db.execSQL(SQL_CREATE_ITEMS_TABLE);

        String SQL_CREATE_ITEMS_TABLE1 = "CREATE TABLE  "+CAR_DIARY_REPAIRS_TABLE+" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nameRepair  VARCHAR2(255),\n" +
                "description VARCHAR2(500),\n" +
                "createDate  DATE );";
        db.execSQL(SQL_CREATE_ITEMS_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // изтриваме предходната таблица при обновяването
        db.execSQL("DROP TABLE IF EXISTS "+CAR_DIARY_FUEL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+CAR_DIARY_REPAIRS_TABLE);
        // създаваме нов екземпляр от таблицата
        onCreate(db);
    }
}
