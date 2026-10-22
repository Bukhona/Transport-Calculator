package com.example.transportapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TransportDB";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE reports (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transportType TEXT, " +
                "dailyCost REAL, " +
                "monthlyCost REAL, " +
                "monthlyDistance REAL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void saveReport(String transportType, double dailyCost,
                           double monthlyCost, double monthlyDistance) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO reports " +
                "(transportType, dailyCost, monthlyCost, monthlyDistance) " +
                "VALUES (?, ?, ?, ?)";

        db.execSQL(query, new Object[]{
                transportType,
                dailyCost,
                monthlyCost,
                monthlyDistance
        });

        db.close();

    }

    public ArrayList<String> getReports() {

        ArrayList<String> reports = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT transportType, dailyCost, monthlyCost, monthlyDistance FROM reports",
                null
        );

        while (cursor.moveToNext()) {

            String transportType = cursor.getString(0);
            double dailyCost = cursor.getDouble(1);
            double monthlyCost = cursor.getDouble(2);
            double monthlyDistance = cursor.getDouble(3);

            String report = transportType
                    + " | Daily: R" + String.format("%.2f", dailyCost)
                    + " | Monthly: R" + String.format("%.2f", monthlyCost)
                    + " | Distance: " + String.format("%.2f", monthlyDistance) + " km";

            reports.add(report);
        }

        cursor.close();
        db.close();

        return reports;
    }
}
