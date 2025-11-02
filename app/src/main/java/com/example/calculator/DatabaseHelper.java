package com.example.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Calculator.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HISTORY = "calculation_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INPUT = "input";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INPUT + " TEXT, " +
                COLUMN_RESULT + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    public void addCalculation(CalculationHistory history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INPUT, history.getInput());
        values.put(COLUMN_RESULT, history.getResult());
        values.put(COLUMN_TYPE, history.getType());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public List<CalculationHistory> getAllCalculations() {
        List<CalculationHistory> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HISTORY, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                CalculationHistory history = new CalculationHistory(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INPUT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESULT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                );
                history.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return historyList;
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, null, null);
        db.close();
    }
}