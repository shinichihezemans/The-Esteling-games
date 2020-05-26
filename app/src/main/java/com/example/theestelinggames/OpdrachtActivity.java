package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class OpdrachtActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private boolean bluetoothAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAvailable = (bluetoothAdapter != null);

        if (bluetoothAvailable) {
            if (!bluetoothAdapter.isEnabled() && requestBluetooth()) {

            }
        } else {
            Toast.makeText(this, "device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean requestBluetooth() {
        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetooth, 0);
        return bluetoothAdapter.isEnabled();
    }
}
