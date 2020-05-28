package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ItemDetail extends AppCompatActivity {
    private static final String LOGTAG = ItemDetail.class.getName();

    public static final String ASSIGNMENT_ID = "AssignmentID";

    private BluetoothAdapter bluetoothAdapter;
    private Assignment assignment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_item_detail);

        int id = getIntent().getExtras().getInt(ASSIGNMENT_ID);
        Log.d(LOGTAG, "onCreate called with EXTRA_ZODIAC_ID = " + id);

         this.assignment = Assignment.getStaticAssignment(id);
        Log.d(LOGTAG, "Assignment[id] = " + this.assignment.getName() + " " + this.assignment.getAttempts());

        TextView minigameName = (TextView) findViewById(R.id.minigameName);
        minigameName.setText(this.assignment.getName());

        TextView forecast = (TextView) findViewById(R.id.minigameIntroduction);
        forecast.setText("TODO");

//        ImageView attractionImage = (ImageView) findViewById();
//        attractionImage.setImageResource();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public void onConnectButtonClicked(View view) {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "bluetooth not supported", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isDiscovering()) {//opnieuw starten
                bluetoothAdapter.cancelDiscovery();
            }
            checkBTPermissions();
            if (bluetoothAdapter.startDiscovery()) {
                //If discovery has started, then display the following toast....//
                Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...",
                        Toast.LENGTH_SHORT).show();
                IntentFilter bluetoothActionFoundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(broadcastReceiver, bluetoothActionFoundFilter);
            } else {
                //If discovery hasn’t started, then display this alternative toast//
                Toast.makeText(getApplicationContext(), "Something went wrong! Discovery has failed to start.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("BLUETOOTH DEVICE FOUND", "DEVICE: " + device.getName());

                tryConnect(device);
            }
        }
    };

    private void tryConnect(BluetoothDevice device){
        if (device != null){
            if (device.getName() != null){
                if (device.getName().equals(this.assignment.getName())){
                    device.createBond();
                    Intent intent = new Intent(this, OpdrachtActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }
}
