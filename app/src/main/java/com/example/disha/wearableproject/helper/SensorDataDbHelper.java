package com.example.disha.wearableproject.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.disha.wearableproject.SensorDataContract;

/**
 * Created by mohni on 11/29/2017.
 */

public class SensorDataDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sensorData.db";

    private static final int DATABASE_VERSION = 1;  //force users to update app

    public SensorDataDbHelper(Context context){ //source of error
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //cursor activity =null
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + SensorDataContract.SensorDataEntry.TABLE_NAME
                + " ( " + SensorDataContract.SensorDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_X + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Y + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Z + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_X + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Y + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Z + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                " ); ";

        Log.d("TABLE_CREATE_Q",SQL_CREATE_WAITLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + SensorDataContract.SensorDataEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
