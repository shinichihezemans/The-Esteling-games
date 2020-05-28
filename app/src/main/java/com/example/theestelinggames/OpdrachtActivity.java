package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

public class OpdrachtActivity extends AppCompatActivity {
    private boolean isStarted = false;//if not started button visible and textView gone else button gone and textView visible

    private TextView assignmentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdracht);
        assignmentTextView = findViewById(R.id.assignmentTextView);
        assignmentTextView.setVisibility(View.GONE);
    }


    public void onStartButtonClicked(View view) {
        this.isStarted = true;
        view.setVisibility(View.GONE);
        assignmentTextView.setVisibility(View.VISIBLE);
    }
}
