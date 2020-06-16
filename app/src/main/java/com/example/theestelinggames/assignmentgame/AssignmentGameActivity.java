package com.example.theestelinggames.assignmentgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.theestelinggames.assignmentdetail.AssignmentDetailActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.Assignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentGameActivity extends AppCompatActivity implements OnBTReceive {

    private int score;

    private Assignment assignment;

    private CardView button;
    private TextView buttonText;
    private ProgressBar progressBar;
    private TextView assignmentTextView;

    private BluetoothIOThread bluetoothIOThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);
        this.button = findViewById(R.id.assessmentButton);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartButtonClicked(v);
            }
        });
        this.button.setEnabled(false);
        this.buttonText = findViewById(R.id.assessmentButtonText);
        this.progressBar = findViewById(R.id.progressBarAssessment);
        assignmentTextView = findViewById(R.id.assignmentTextView);
        assignmentTextView.setVisibility(View.GONE);
        BluetoothDevice device = getIntent().getParcelableExtra(AssignmentDetailActivity.DEVICE_KEY);
        this.assignment = Assignment.getAssignment(this, getIntent().getExtras().getInt(Assignment.SHARED_PREFERENCES));
        this.score = 1;

        bluetoothIOThread = null;

        if(device != null) {
            Thread connectThread = new ConnectThread(device, this);
            connectThread.start();
        }
    }

    /**
     * This handler handles the messaging
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            onReceive((String) msg.obj);
            super.handleMessage(msg);
        }
    };

    public void onStartButtonClicked(View view) {
        this.bluetoothIOThread.writeStart();
    }

    /**
     * When the bluetoothIOThead receives a message this callback will be called.
     * @param msg the message sent by the ESP.
     */
    public void onReceive(String msg){
        Log.d("THREAD", msg);

        if(msg.contains("START")){
            this.buttonText.setText("GO!");
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
                assignmentTextView.setText(this.assignment.getAssignments()[(Integer.parseInt(m.group()) - 1)]);
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

    /**
     * When the bluetoothSocket is accepted this callback will be called by the BluetoothIoThread.
     */
    public void onConnected() {
        Log.d("THREAD", "Connected");
        this.button.setEnabled(true);
        this.buttonText.setText("START");
        this.progressBar.setVisibility(View.GONE);
    }

    /**
     * When the bluetoothSocket is disconnected this callback will be called by the BluetoothIoThread.
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
    public Handler getHandler() {
        return this.handler;
    }

    @Override
    public void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread) {
        this.bluetoothIOThread = bluetoothIOThread;
    }
}
