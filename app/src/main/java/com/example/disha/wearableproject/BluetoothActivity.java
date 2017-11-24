/**
 * Created by mohanish on 11/22/2017.
 */

package com.example.disha.wearableproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    private static final String TAG = "BluetoothActivity";
    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter mBluetoothAdapter;

    private ArrayList<BluetoothDevice> mBTNewDevices = new ArrayList<>();
    private ArrayList<BluetoothDevice> mBTPairedDevices = new ArrayList<>();
    private DeviceListAdapter mNewDeviceListAdapter;
    private DeviceListAdapter mPairedDeviceListAdapter;
    private ListView pairedListView;
    private ListView newListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_list_view);

        mPairedDeviceListAdapter = new DeviceListAdapter(this, mBTPairedDevices);
        pairedListView = (ListView) findViewById(R.id.lvPairedDevices);
        pairedListView.setAdapter(mPairedDeviceListAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        mNewDeviceListAdapter = new DeviceListAdapter(this, mBTNewDevices);
        newListView = (ListView) findViewById(R.id.lvNewDevices);
        newListView.setAdapter(mNewDeviceListAdapter);
        newListView.setOnItemClickListener(mDeviceClickListener);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "onCreate: Does not have BT capabilities.");
            new AlertDialog.Builder(this)
                           .setTitle("Not compatible")
                           .setMessage("Your phone does not support Bluetooth")
                           .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                               @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           })
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .show();
        }

        Button scanButton = (Button) findViewById(R.id.buttonScan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onStart();
            }
        });

        Button skipButton = (Button) findViewById(R.id.buttonSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto next activity and turn off the bluetooth
                mBluetoothAdapter.disable();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else listDevices();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: Bluetooth is On.");
                listDevices();
            }
            if(resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: Error occurred while enabling bluetooth.");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void listDevices() {
        ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            mBTPairedDevices.clear();
            for (BluetoothDevice device : pairedDevices) {
                mBTPairedDevices.add(device);
                mPairedDeviceListAdapter.notifyDataSetChanged();
            }
            ((TextView) findViewById(R.id.tvNoPairedDevices)).setVisibility(View.GONE);
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Quick permission check
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }

        // Register for broadcasts when a device is discovered.
        registerReceiver(mBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(mBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
        //registerReceiver(mBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));

        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Don't forget to unregister the broadcast listener.
        unregisterReceiver(mBroadcastReceiver);
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                Log.d(TAG, "onReceive: ACTION_FOUND.");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already in list or paired, skip it, because it's been listed already
                if ((!mBTNewDevices.contains(device)) && (!mBTPairedDevices.contains(device))) {
                    mBTNewDevices.add(device);
                    mNewDeviceListAdapter.notifyDataSetChanged();
                }
            }

            // When discovery cycle finished
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mBTNewDevices.isEmpty()) {
                    ((TextView) findViewById(R.id.tvNoNewDevices)).setVisibility(View.VISIBLE);
                }
                ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
            }

            // When device is paired
            /*if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.d(TAG, "onReceive: ACTION_BOND_STATE_CHANGED.");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "onReceive: BOND_BONDING.");
                }
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "onReceive: BOND_BONDED.");
                    //new ConnectThread(device);
                }
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "onReceive: BOND_NONE.");
                }
            }*/
        }
    };

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mBluetoothAdapter.cancelDiscovery();
            //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            if (mBluetoothAdapter.isEnabled()) {
                new ConnectThread((BluetoothDevice) parent.getAdapter().getItem(position));
            }
            else Toast.makeText(getApplicationContext(),"Please turn on bluetooth",Toast.LENGTH_SHORT).show();
        }
    };

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            Log.d(TAG, "ConnectThread: Constructor called.");
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.d(TAG, "ConnectThread: Thread started.");
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            Log.d(TAG, "ConnectThread: Device connected.");
            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }
}
