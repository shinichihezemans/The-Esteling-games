package com.example.theestelinggames.assignmentgame;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentdetail.AssignmentDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which displays the instructions of the game.
 */
public class AssignmentGameActivity extends AppCompatActivity implements OnBTReceive {

    private int score;

    private Button button;
    private TextView assignmentTextView;

    private AssignmentContainer selectedAssignment;
    private BluetoothIOThread bluetoothIOThread;

    /**
     * Start method of the activity.
     * Adds the objectives to a list and connects it with the Assignment.
     * Also starts the thread.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);
        this.button = findViewById(R.id.assignmentStartButton);
        this.button.setEnabled(false);
        assignmentTextView = findViewById(R.id.assignmentTextView);
        assignmentTextView.setVisibility(View.GONE);
        BluetoothDevice device =
                getIntent().getParcelableExtra(AssignmentDetailActivity.DEVICE_KEY);

        this.score = 1;

        List<String> temp = new ArrayList<>();
        temp.add("BUTTON 1 (Geel)");
        temp.add("BUTTON 2 (Rood)");
        temp.add("BUTTON 3 (Geel)");
        temp.add("TOUCHSENSOR!");

        this.selectedAssignment = new AssignmentContainer("Johan en de Eenhoorn", temp);
        bluetoothIOThread = null;

        if (device != null) {
            Thread connectThread = new ConnectThread(device, this);
            connectThread.start();
        }
    }

    /**
     * Creates the handler to handle the incoming and outgoing messages.
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            onReceive((String) msg.obj);
            super.handleMessage(msg);
        }
    };

    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets the onClick method for the chosen view.
     *
     * @param view The view that is clicked.
     */
    public void onStartButtonClicked(View view) {
        this.bluetoothIOThread.writeUTF();
    }

    /**
     * Performs an action based on the received message.
     *
     * @param msg The message that is receive.
     */
    public void onReceive(String msg) {
        Log.d("THREAD", msg);

        if (msg.contains("START")) {
            button.setText(R.string.GO);
            button.setEnabled(false);
            assignmentTextView.setVisibility(View.VISIBLE);
        }
        if (msg.equals("STOP")) {
            onDisconnect(0);
        }
        if (msg.contains("TASK")) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(msg);
            if (m.find()) {
                Log.d("THREAD", m.group());
                assignmentTextView.setText(this.selectedAssignment.getAssignments().get(
                        Integer.parseInt(m.group()) - 1));
            }
        }
        if (msg.contains("CONNECTED")) {
            onConnected();
        }
        if (msg.contains("DISCONNECTED")) {
            onDisconnect(this.score);
        }
        if (msg.contains("FINNISH")) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(msg);
            if (m.find()) {
                this.score = Integer.parseInt(m.group());
                Log.d("THREAD", "SCORE: " + this.score);
            }
            this.bluetoothIOThread.cancel();
        }
    }

    /**
     * Sets the state of the button if the app is connected with the ESP module.
     */
    public void onConnected() {
        Log.d("THREAD", "Connected");
        this.button.setEnabled(true);
    }

    /**
     * Makes sure the thread finishes when the bluetooth is disconnected.
     *
     * @param scoreResult The score that is achieved.
     */
    public void onDisconnect(int scoreResult) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", scoreResult);

        Log.d("THREAD", "ONDISCONNECT! scoreresult: " + scoreResult);

        if (scoreResult > 0) {
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }

    /**
     * Sets the variable bluetoothIOThread.
     *
     * @param connectThread     The connection threads of the bluetooth device.
     * @param bluetoothIOThread The bluetooth IO threads of the bluetooth device.
     */
    @Override
    public void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread) {
        this.bluetoothIOThread = bluetoothIOThread;
    }
}
