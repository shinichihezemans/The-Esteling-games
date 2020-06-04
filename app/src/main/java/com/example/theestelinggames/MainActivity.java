package com.example.theestelinggames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theestelinggames.iconscreen.CharacterActivity;

public class MainActivity extends AppCompatActivity {

    private View buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonView = findViewById(R.id.startScreenButton);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        final Intent intent = new Intent(this, AssignmentView.class);
        final Intent intent = new Intent(this, CharacterActivity.class);

        buttonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View PopupView = v;
                PopupView.setVisibility(View.GONE);
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
                PopupView.setVisibility(View.VISIBLE);
            }

        });
    }
}


