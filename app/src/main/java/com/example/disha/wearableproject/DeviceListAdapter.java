package com.example.disha.wearableproject;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohanish on 11/22/2017.
 */

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {
    private ArrayList<BluetoothDevice> mDevices;
    private LayoutInflater mLayoutInflater;

    public DeviceListAdapter(Context context, ArrayList<BluetoothDevice> devices) {
        super(context, 0, devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.device_adapter_view, null);
        BluetoothDevice device = mDevices.get(position);
        // Lookup view for data population
        TextView deviceName = (TextView) convertView.findViewById(R.id.tvDeviceName);
        TextView deviceAddress = (TextView) convertView.findViewById(R.id.tvDeviceAddress);
        // Populate the data into the template view using the data object
        deviceName.setText(device.getName());
        deviceAddress.setText(device.getAddress());
        // Return the completed view to render on screen
        return convertView;
    }
}
