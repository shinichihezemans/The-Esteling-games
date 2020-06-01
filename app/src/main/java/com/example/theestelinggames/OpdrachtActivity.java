package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OpdrachtActivity extends AppCompatActivity {
    private boolean isStarted = false;//if not started button visible and textView gone else button gone and textView visible

    private TextView assignmentTextView;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private DataOutputStream dataOutputStream;

    private static final String LOGTAG = ItemDetail.class.getName();
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);
        assignmentTextView = findViewById(R.id.assignmentTextView);
        assignmentTextView.setVisibility(View.GONE);
        this.device = getIntent().getParcelableExtra(ItemDetail.DEVICE_KEY);
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Toast.makeText(getApplicationContext(), "Initialising socket!",
                Toast.LENGTH_SHORT).show();

        try {
            this.socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            Toast.makeText(getApplicationContext(), "Initialized socket!",
                    Toast.LENGTH_SHORT).show();
            Log.i(LOGTAG, "got socket");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.bluetoothAdapter.cancelDiscovery();

        if (this.socket != null) {
            Toast.makeText(getApplicationContext(), "socket niet null",
                    Toast.LENGTH_SHORT).show();
            Log.i(LOGTAG, "socket niet null");
            try {
                Toast.makeText(getApplicationContext(), "trying to connect",
                        Toast.LENGTH_SHORT).show();
                Log.i(LOGTAG, "trying to connect");
                this.socket.connect();
                Toast.makeText(getApplicationContext(), "connected",
                        Toast.LENGTH_SHORT).show();
                Log.i(LOGTAG, "connected");

                this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                dataOutputStream.writeUTF("hi");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Toast.makeText(getApplicationContext(), "closing",
                            Toast.LENGTH_SHORT).show();
                    Log.i(LOGTAG, "closing");
                    this.socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "socket null",
                    Toast.LENGTH_SHORT).show();
            Log.i(LOGTAG, "socket null");
        }
    }

    public void onStartButtonClicked(View view) {
        this.isStarted = true;
        //view.setVisibility(View.GONE);
        //assignmentTextView.setVisibility(View.VISIBLE);]
        try {
            this.dataOutputStream.write(50);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
