package com.example.theestelinggames.startscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.Assignment;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    public static final String globalInfo = "globalInfo";
    private static final String DATE_KEY = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSharedPreferences();
        setContentView(R.layout.activity_start);

        ImageView imageView = findViewById(R.id.logoImageView);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        final Intent intent = new Intent(this, AssignmentView.class);
        final Intent intent = new Intent(this, CharacterActivity.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setVisibility(View.GONE);
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
                v.setVisibility(View.VISIBLE);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
        }
    }

    private void checkSharedPreferences() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            if (LocalDate.parse(getSharedPreferences(globalInfo, MODE_PRIVATE).getString(DATE_KEY, LocalDate.MIN.toString())).isBefore(now)) {
                wipeSharedPreferences();
                getSharedPreferences(globalInfo, MODE_PRIVATE)
                        .edit()
                        .putString(DATE_KEY, now.toString())
                        .apply();
            }
        }else {
            Toast.makeText(this,"api level low some features are not supported", Toast.LENGTH_LONG).show();
        }
        if (!getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
        }
    }

    private void wipeSharedPreferences() {
        SharedPreferences sharedPreferences;
        String[] files = {globalInfo, CharacterActivity.USERCREDENTIALS, Assignment.SHARED_PREFERENCES};

        for (String fileName : files) {
            sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .clear()
                    .commit();
//            Log.i("wipe sharedpreferences", " keys are now" + sharedPreferences.getAll().keySet());
        }
    }
}


