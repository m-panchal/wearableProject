package com.example.disha.wearableproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by disha on 11/20/2017.
 */

public class DatabaseHistoryHandler extends SQLiteOpenHelper {
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "AndroidDatabase";


    // table name
    private static final String TABLE_USER_HISTORY = "history";
    private static final String TABLE_USER = "user";
    private static final String TABLE_REGISTER = "register";

    // Login Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // Register Table Columns names
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAILID = "email";
    private static final String KEY_PWD = "password";

    // History Table Columns names
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_RACE = "race";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_STATUS = "status";
    private static final String KEY_EDUCATION = "education";
    private static final String KEY_HEALTH = "health";
    private static final String KEY_DIAGONISED_DISEASE = "diagonisedDisease";
    private static final String KEY_MEDICATION = "medication";
    private static final String KEY_ALCOHOL_USE = "alcoholUse";

    private static final String TAG = DatabaseHistoryHandler.class.getSimpleName();

    public DatabaseHistoryHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT"
                + ");";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_HISTORY + "("
                + KEY_AGE + " TEXT,"
                + KEY_GENDER + " TEXT," + KEY_RACE + " TEXT,"
                + KEY_HEIGHT + " TEXT," + KEY_WEIGHT + " TEXT,"
                + KEY_STATUS + " TEXT," + KEY_EDUCATION + " TEXT,"
                + KEY_HEALTH + " TEXT," + KEY_DIAGONISED_DISEASE + " TEXT,"
                + KEY_MEDICATION + " TEXT," + KEY_ALCOHOL_USE + " TEXT" + ");";
        db.execSQL(CREATE_HISTORY_TABLE);

        String CREATE_REGISTER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_REGISTER + "("
                + KEY_USERNAME + " TEXT," + KEY_PHONE + " TEXT," + KEY_EMAILID + " TEXT,"
                + KEY_PWD + " TEXT" + ");";
        db.execSQL(CREATE_REGISTER_TABLE);

        Log.d(TAG, "Created:Database tables");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD_DETAILS);
        // Create tables again
        onCreate(db);
    }

    // Insert User Login
    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);

        // Inserting Row
        long ID = db.insert(TABLE_USER, null, values);
        Log.d(TAG, "Inserted New USER: " + ID);
        db.close(); // Closing database connection
    }

    // Adding new History
    public void addUserHistory(String age, String gender, String race, String height, String weight,
                               String status, String education, String health, String diagonisedDisease,
                               String medication, String alcoholUse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AGE, age);
        values.put(KEY_GENDER, gender);
        values.put(KEY_RACE, race);
        values.put(KEY_HEIGHT, height);
        values.put(KEY_WEIGHT, weight);
        values.put(KEY_STATUS, status);
        values.put(KEY_EDUCATION, education);
        values.put(KEY_HEALTH, health);
        values.put(KEY_DIAGONISED_DISEASE, diagonisedDisease);
        values.put(KEY_MEDICATION, medication);
        values.put(KEY_ALCOHOL_USE, alcoholUse);

        // Inserting Row
        long ID = db.insert(TABLE_USER_HISTORY, null, values);
        Log.d(TAG, "Inserted USER History: " + ID);
        db.close(); // Closing database connection
    }

    // Register user
    public void registerUser(String name, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAILID, email);
        values.put(KEY_PWD,password);

        // Inserting Row
        long ID = db.insert(TABLE_USER, null, values);
        Log.d(TAG, "Registered New USER: " + ID);
        db.close(); // Closing database connection
    }



    // Deleting table details
    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_HISTORY, null, null);
        db.close();
        Log.d(TAG, "Deleted User details");
    }

}
