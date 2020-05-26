package com.example.theestelinggames;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Button button = findViewById(R.id.startScreenButton);
        final Intent intent = new Intent(this, AssignmentView.class);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder.setMessage(R.string.PopUpText);
                builder.setPositiveButton(R.string.StartButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
    }
}


