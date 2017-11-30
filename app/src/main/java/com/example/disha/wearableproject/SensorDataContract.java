package com.example.disha.wearableproject;

import android.provider.BaseColumns;

/**
 * Created by mohni on 11/29/2017.
 */

public class SensorDataContract {
    public static final class SensorDataEntry implements BaseColumns {

        public static final String TABLE_NAME = "sensorData";
        public static final String COLUMN_ACCELEROMETER="accelerometerMagnitude";
        public static final String COLUMN_ACCELEROMETER_X="accelerometerX";
        public static final String COLUMN_ACCELEROMETER_Y="accelerometerY";
        public static final String COLUMN_ACCELEROMETER_Z="accelerometerZ";
        public static final String COLUMN_GYROSCOPE ="gyroscopeMagnitude";
        public static final String COLUMN_GYROSCOPE_X ="gyroscopeX";
        public static final String COLUMN_GYROSCOPE_Y ="gyroscopeY";
        public static final String COLUMN_GYROSCOPE_Z ="gyroscopeZ";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
