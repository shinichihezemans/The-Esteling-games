package com.example.theestelinggames.assignmentgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.theestelinggames.assignmentdetail.ItemDetailActivity;
import com.example.theestelinggames.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class which shows the mini-game objectives and relays information back via bluetooth.
 */
public class OpdrachtActivity extends AppCompatActivity implements OnBTReceive {

    private int score;

    private Button button;
    private TextView assignmentTextView;

    private AssignmentContainer selectedAssignment;
    private BluetoothIOThread bluetoothIOThread;

    /**
     * creates the activity screen and adds the objectives to a list and connects it with the Assignment.
     * Also starts the thread
     * @param savedInstanceState to create the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);
        this.button = findViewById(R.id.assignmentStartButton);
        this.button.setEnabled(false);
        assignmentTextView = findViewById(R.id.assignmentTextView);
        assignmentTextView.setVisibility(View.GONE);
        BluetoothDevice device = getIntent().getParcelableExtra(ItemDetailActivity.DEVICE_KEY);

        this.score = 1;

        List<String> temp = new ArrayList<>();
        temp.add("BUTTON 1 (Geel)");
        temp.add("BUTTON 2 (Rood)");
        temp.add("BUTTON 3 (Geel)");
        temp.add("TOUCHSENSOR!");

        this.selectedAssignment = new AssignmentContainer("Johan en de Eenhoorn", "achtbaan", temp);
        bluetoothIOThread = null;

        if(device != null) {
            Thread connectThread = new ConnectThread(device, this);
            connectThread.start();
        }
    }

    /**
     * makes handler to handle the incoming and outgoing messages
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            onReceive((String) msg.obj);
            super.handleMessage(msg);
        }
    };

    public Handler handler() {
        return handler;
    }

    public void onStartButtonClicked(View view) {
        this.bluetoothIOThread.writeUTF("start");
    }

    /**
     * performs an action based on the received message.
     * @param msg the message that is received
     */
    public void onReceive(String msg){
        Log.d("THREAD", msg);

        if(msg.contains("START")){
            button.setText("GO!");
            button.setEnabled(false);
            assignmentTextView.setVisibility(View.VISIBLE);
        }
        if(msg.equals("STOP")){
            onDisconnect(0);
        }
        if(msg.contains("TASK")){
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(msg);
            if(m.find()) {
                Log.d("THREAD", m.group());
                assignmentTextView.setText(this.selectedAssignment.getAssignments().get(Integer.parseInt(m.group()) - 1));
            }
        }
        if(msg.contains("CONNECTED")){
            onConnected();
        }
        if(msg.contains("DISCONNECTED")){
            onDisconnect(this.score);
        }
        if(msg.contains("FINNISH")){
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(msg);
            if(m.find()) {
                this.score = Integer.parseInt(m.group());
                Log.d("THREAD", "SCORE: " + this.score);
            }
            this.bluetoothIOThread.cancel();
        }
    }


    public void onConnected() {
        Log.d("THREAD", "Connected");
        this.button.setEnabled(true);
    }

    /**
     * makes sure the thread finishes when the bluetooth is disconnected.
     * @param scoreResult the score that is achieved.
     */
    public void onDisconnect(int scoreResult) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", scoreResult);

        Log.d("THREAD", "ONDISCONNECT! scoreresult: " + scoreResult);

        if(scoreResult > 0) {
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }

    @Override
    public void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread) {
        this.bluetoothIOThread = bluetoothIOThread;
    }
}
