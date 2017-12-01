package com.example.disha.wearableproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disha.wearableproject.helper.SensorDataDbHelper;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_GYROSCOPE;

/**
 * Created by ${disha} on ${11/20/2017}.
 */

public class TremorKineticActivity extends AppCompatActivity implements SensorEventListener {

    private static ImageView play;
    private static TextView counter;
    private static Thread thread;
    private float mMagnitude = 0;
    private static int mAFlag = 0;
    private static int mGFlag = 0;
    private static float mAccelerometerMagnitude;
    private static float mAccelerometerX;
    private static float mAccelerometerY;
    private static float mAccelerometerZ;
    private static float mGyroscopeMagnitude;
    private static float mGyroscopeX;
    private static float mGyroscopeY;
    private static float mGyroscopeZ;

    private static final String TAG = "MyActivity";

    private SQLiteDatabase mDb;
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private Sensor mGyroSensor;
    private Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tremor_kinetic);

        counter = (TextView) findViewById(R.id.ctrkinetic);
        play = (ImageView) findViewById(R.id.playbtnkinetic);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                counter.setVisibility(View.VISIBLE);
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SensorDataDbHelper dbHelper = new SensorDataDbHelper(TremorKineticActivity.this);
                        mDb = dbHelper.getWritableDatabase();
                        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        mAccelSensor = mSensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
                        mGyroSensor = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE);
                        mSensorManager.registerListener(TremorKineticActivity.this, mAccelSensor, 20);
                        mSensorManager.registerListener(TremorKineticActivity.this, mGyroSensor, 20);
                        Log.d("Thread", "Started");
                        counter.post(new Runnable() {
                            public void run() {
                                counter.setText("Collecting\n data...");
                            }
                        });
                        while (count < 20) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count++;
                        }
                        counter.post(new Runnable() {
                            public void run() {
                                counter.setText("Done!");
                                Intent i = new Intent(TremorKineticActivity.this, TremorActivity.class);
                                startActivity(i);
                                finish();

                            }
                        });
                        mSensorManager.unregisterListener(TremorKineticActivity.this);
                        mSensorManager.unregisterListener(TremorKineticActivity.this);
                        mDb.close();
                    }
                });
                thread.start();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // If sensor is unreliable, then just return
        Log.d("onSensorChanged", "Received event " + String.valueOf(event.accuracy));
        /*if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }*/
        mMagnitude = (float) Math.sqrt((float) Math.pow(event.values[0], 2) + (float) Math.pow(event.values[1], 2) + (float) Math.pow(event.values[2], 2));

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ++mAFlag;
            //get accelerometer values
            mAccelerometerMagnitude = mMagnitude;
            mAccelerometerX = event.values[0];
            mAccelerometerY = event.values[1];
            mAccelerometerZ = event.values[2];
            if ((mAFlag == 1) && (mGFlag == 1)) {
                addNewSensorData(mAccelerometerMagnitude, mAccelerometerX, mAccelerometerY, mAccelerometerZ, mGyroscopeMagnitude, mGyroscopeX, mGyroscopeY, mGyroscopeZ);
                mAFlag = 0;
                mGFlag = 0;
            } else {
                if (mAFlag > 1) {
                    mAFlag = 1;
                }
                if (mGFlag > 1) {
                    mGFlag = 1;
                }
            }

        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            ++mGFlag;
            //get gyroscope values
            mGyroscopeMagnitude = mMagnitude;
            mGyroscopeX = event.values[0];
            mGyroscopeY = event.values[1];
            mGyroscopeZ = event.values[2];
            if ((mAFlag == 1) && (mGFlag == 1)) {
                addNewSensorData(mAccelerometerMagnitude, mAccelerometerX, mAccelerometerY, mAccelerometerZ, mGyroscopeMagnitude, mGyroscopeX, mGyroscopeY, mGyroscopeZ);
                mAFlag = 0;
                mGFlag = 0;
            } else {
                if (mAFlag > 1) {
                    mAFlag = 1;
                }
                if (mGFlag > 1) {
                    mGFlag = 1;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private long addNewSensorData(float accelerometerMagnitude, float accelerometerX, float accelerometerY, float accelerometerZ, float gyroscopeMagnitude, float gyroscopeX, float gyroscopeY, float gyroscopeZ) {

        Log.d(TAG, "-accelerometerMagnitude" + accelerometerMagnitude);
        Log.d(TAG, "-accelerometerX" + accelerometerX);
        Log.d(TAG, "-accelerometerY" + accelerometerY);
        Log.d(TAG, "-accelerometerZ" + accelerometerZ);
        Log.d(TAG, "-gyroscopeMagnitude" + gyroscopeMagnitude);
        Log.d(TAG, "-GyroscopeX" + mGyroscopeX);
        Log.d(TAG, "-GyroscopeY" + mGyroscopeY);
        Log.d(TAG, "-GyroscopeZ" + mGyroscopeZ);
        ContentValues cv = new ContentValues();
        String value = "Kinetic";
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_VALUE, value);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER, accelerometerMagnitude);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_X, accelerometerX);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Y, accelerometerY);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Z, accelerometerZ);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE, gyroscopeMagnitude);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_X, gyroscopeX);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Y, gyroscopeY);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Z, gyroscopeZ);
        Log.d("addNewSensorData", "Added");
        return mDb.insert(SensorDataContract.SensorDataEntry.TABLE_NAME, null, cv);
    }

}