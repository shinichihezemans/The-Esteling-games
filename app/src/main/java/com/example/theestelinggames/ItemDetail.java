package com.example.theestelinggames;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ItemDetail extends AppCompatActivity {

    private static final String LOGTAG = ItemDetail.class.getName();
    public static final String ASSIGNMENT_ID = "AssignmentID";
    public static final String DEVICE_KEY = "DEVICE_KEY";

    private TextView highScoreLabel;

    private BluetoothAdapter bluetoothAdapter;
    private Assignment assignment;

    private int highScore;

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

        this.highScoreLabel = (TextView) findViewById(R.id.minigameIntroduction);
        highScoreLabel.setText("High score: ");

//        ImageView attractionImage = (ImageView) findViewById();
//        attractionImage.setImageResource();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void onConnectButtonClicked(View view) {
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "bluetooth not supported", Toast.LENGTH_SHORT).show();
        } else {

            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }

            if (bluetoothAdapter.isDiscovering()) {//opnieuw starten
                bluetoothAdapter.cancelDiscovery();
            }
            checkBTPermissions();

            //is null if not in bonded devices.
            BluetoothDevice bluetoothDevice = this.getBluetoothDevice();
            if (this.hasBonded(bluetoothDevice)) {
                onConnected(bluetoothDevice);
                return;
            }

            if (bluetoothAdapter.startDiscovery()) {
                //If discovery has started, then display the following toast....//
                Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...",
                        Toast.LENGTH_SHORT).show();
                IntentFilter bluetoothFilter = new IntentFilter();
                bluetoothFilter.addAction(BluetoothDevice.ACTION_FOUND);
                bluetoothFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

                registerReceiver(broadcastReceiver, bluetoothFilter);
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

            BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();

            if (bluetoothDevice == null || action == null || bluetoothDevice.getName() == null) {
                return;
            }

            if (bluetoothDevice.getName().equals(assignment.getName())) {
                if (BluetoothDevice.ACTION_FOUND.equals(action) && !hasBonded(bluetoothDevice)) {
                    Toast.makeText(getApplicationContext(), "Creating bond!",
                            Toast.LENGTH_SHORT).show();

                    bluetoothDevice.createBond();
                }

                if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action) &&
                        bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    onConnected(bluetoothDevice);
                }
            }
        }
    };

    private BluetoothDevice getBluetoothDevice() {
        for (BluetoothDevice bluetoothDevice : this.bluetoothAdapter.getBondedDevices()) {
            if (bluetoothDevice.getName().equals(this.assignment.getName())) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    private boolean hasBonded(BluetoothDevice searchDevice) {
        if (searchDevice != null) {
            for (BluetoothDevice bluetoothDevice : this.bluetoothAdapter.getBondedDevices()) {
                if (bluetoothDevice.getAddress().equals(searchDevice.getAddress())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }

        Log.d("THREAD", "" + data.getIntExtra("result", 0));

        if(data.hasExtra("result")){
            int score = data.getIntExtra("result", 0);

            if(this.highScore < score){
                this.highScore = score;
                this.updateHighScore();
                Log.d("THREAD", "High score set!");
            }
        }
    }

    private void updateHighScore(){
        this.highScoreLabel.setText("High score: " + this.highScore);
    }

    private void onConnected(BluetoothDevice bluetoothDevice) {
        Intent assignmentIntent = new Intent(ItemDetail.this, OpdrachtActivity.class);
        assignmentIntent.putExtra(DEVICE_KEY, bluetoothDevice);
        startActivityForResult(assignmentIntent, 1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
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
