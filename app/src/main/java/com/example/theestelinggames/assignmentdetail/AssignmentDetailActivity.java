package com.example.theestelinggames.assignmentdetail;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentgame.AssignmentGameActivity;
import com.example.theestelinggames.assignmentlist.Assignment;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;

/**
 * Class in which the assignment details are displayed. Gives the user the option to play the game.
 */
public class AssignmentDetailActivity extends AppCompatActivity {

    private static final String LOGTAG = AssignmentDetailActivity.class.getName();
    public static final String ASSIGNMENT_ID = "AssignmentID";
    public static final String DEVICE_KEY = "DEVICE_KEY";

    private BluetoothAdapter bluetoothAdapter;
    private Assignment assignment;

    /**
     * Start method of the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_item_detail);

        //get assignment this activity was called for.
        int id = getIntent().getExtras().getInt(ASSIGNMENT_ID);
        this.assignment = Assignment.getAssignment(this, id);

        //set contents
        TextView minigameName = findViewById(R.id.nameIDTextView);
        minigameName.setText(this.assignment.getName());

        TextView introduction = findViewById(R.id.minigameIntroduction);
        introduction.setText(this.assignment.getInformation());

        ImageView attractionImage = findViewById(R.id.minigamePhoto);
        attractionImage.setImageResource(assignment.getImageResourceId());

        Button button = findViewById(R.id.connectButton);
        button.setEnabled(this.assignment.getAttempts() < 3);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * This method is called when the user presses the play button.
     *
     * @param view View it was called from.
     */
    public void onConnectButtonClicked(View view) {

        //check is you can play
        if (this.assignment.getAttempts() == 3 || this.assignment.getAttempts() > 3) {
            Toast.makeText(this, "You don't have any attempts left!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //check if bluetooth is supported
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "bluetooth not supported", Toast.LENGTH_SHORT).show();
        } else {

            //enable bluetooth
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }

            //stop discovery
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            //ask for permissions from the user
            checkBTPermissions();

            //is null if not in bonded devices.
            BluetoothDevice bluetoothDevice = this.getBluetoothDevice();
            if (this.hasBonded(bluetoothDevice)) {
                onConnected(bluetoothDevice);
                return;
            }

            if (bluetoothAdapter.startDiscovery()) {
                //If discovery has started, then display the following toast....//
                Toast.makeText(getApplicationContext(),
                        "Discovering other bluetooth devices...",
                        Toast.LENGTH_SHORT).show();
                IntentFilter bluetoothFilter = new IntentFilter();
                bluetoothFilter.addAction(BluetoothDevice.ACTION_FOUND);
                bluetoothFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

                registerReceiver(broadcastReceiver, bluetoothFilter);
            } else {
                //If discovery hasn’t started, then display this alternative toast//
                Toast.makeText(getApplicationContext(),
                        "Something went wrong! Discovery has failed to start.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method is called when the MiniGame returns a result and sets the attempts and HighScore.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        this.assignment.setAttempts(this.assignment.getAttempts() + 1);

        if (data.hasExtra("result")) {
            int score = data.getIntExtra("result", -1);

            if (assignment.getScore() < score) {
                this.updateHighScore(score);
                Log.d("THREAD", "High score set!");
            }
        }

        assignment.saveData();
        assignment.syncWithPreferences();
    }

    /**
     * Updates the players HighScore.
     *
     * @param score The new score the player received after finishing the minigame.
     */
    private void updateHighScore(int score) {
        if (assignment.setHighScore(score)) {
            Toast.makeText(this, "High score: " + score, Toast.LENGTH_SHORT).show();

            //To send message object to server
            SharedPreferences sharedPreferences = getSharedPreferences(
                    CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
            String animalName = getString(sharedPreferences.getInt(
                    CharacterActivity.USERNAMEID_KEY, -1));
            int id = sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);
            String clientID = animalName + " " + id;

            MQTTConnection mqttConnectionSend = new MQTTConnection(
                    this, clientID + "OUT");

            mqttConnectionSend.connectOUT(new Message(id, animalName, score));

            //save and sync score
            assignment.saveData();
            assignment.syncWithPreferences();
        }
    }

    /**
     * Initalizes the broadcastReceiver variable.
     */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice = intent.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();

            //checks
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

    /**
     * Method to get the BluetoothDevice.
     *
     * @return bluetoothDevice Returns a bluetooth device that is already bonded
     * and has the right name.
     */
    private BluetoothDevice getBluetoothDevice() {
        for (BluetoothDevice bluetoothDevice : this.bluetoothAdapter.getBondedDevices()) {
            if (bluetoothDevice.getName().equals(this.assignment.getName())) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    /**
     * @param searchDevice the device to check if it has already bonded
     * @return if searchDevice has already bonded
     */
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

    /**
     * This method is called when the app has connected with the ESP.
     *
     * @param bluetoothDevice The Bluetooth ESP module.
     */
    private void onConnected(BluetoothDevice bluetoothDevice) {
        Intent assignmentIntent = new Intent(AssignmentDetailActivity.this,
                AssignmentGameActivity.class);
        assignmentIntent.putExtra(DEVICE_KEY, bluetoothDevice);
        startActivityForResult(assignmentIntent, 1);
    }

    /**
     * Method is called when the activity is no longer visible to the user.
     */
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
     * Android must programmatically check the permissions for bluetooth.
     * Putting the proper permissions in the manifest is not enough.
     */
    private void checkBTPermissions() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }
}
