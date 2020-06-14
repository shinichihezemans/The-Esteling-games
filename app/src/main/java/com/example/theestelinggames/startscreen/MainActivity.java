package com.example.theestelinggames.startscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.Assignment;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;

import java.time.LocalDate;

/**
 * Class which is used for the start screen of the app. If the user already has chosen a character
 * icon, this activity will not be displayed again unless the user reinstalls the app.
 */
public class MainActivity extends AppCompatActivity {
    private static final String LOGTAG = MainActivity.class.getName();

    public static final String globalInfo = "globalInfo";
    private static final String DATE_KEY = "date";

    /**
     * Start method of the activity.
     * As soon as somebody presses the button it generates the pop-up screen in the onClick method.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate()");
        super.onCreate(savedInstanceState);
        checkSharedPreferences();
        setContentView(R.layout.activity_start);

        ImageView imageView = findViewById(R.id.logoImageView);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Intent intent = new Intent(this, CharacterActivity.class);

        // Creates the pop-up screen and adds the text and functionality
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                builder.setMessage(R.string.PopUpText);
                builder.setPositiveButton(R.string.StartButton,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(R.string.CancelButton,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                v.setVisibility(View.VISIBLE);
            }

        });
    }

    /**
     * If the user already has chosen a character,
     * this method takes them directly to the AssignmentList activity.
     */
    @Override
    protected void onStart() {
        Log.d(LOGTAG, "onStart()");
        super.onStart();
        if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getInt(
                CharacterActivity.ID_KEY, -1) != -1) {
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method checks all the data,
     * if the date has changed it calls the wipeSharedPreferences method to delete the data.
     */
    private void checkSharedPreferences() {
        Log.d(LOGTAG, "checkSharedPreferences()");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            if (LocalDate.parse(getSharedPreferences(globalInfo, MODE_PRIVATE).getString(
                    DATE_KEY, LocalDate.MIN.toString())).isBefore(now)) {
                wipeSharedPreferences();
                getSharedPreferences(globalInfo, MODE_PRIVATE)
                        .edit()
                        .putString(DATE_KEY, now.toString())
                        .apply();
            }
        } else {
            Toast.makeText(this, "api level low some features are not supported",
                    Toast.LENGTH_LONG).show();
        }
        if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getInt(
                CharacterActivity.ID_KEY, -1) != -1) {
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method deletes the saved data.
     */
    private void wipeSharedPreferences() {
        Log.d(LOGTAG, "wipeSharedPreferences()");
        SharedPreferences sharedPreferences;
        String[] files = {globalInfo, CharacterActivity.USERCREDENTIALS, Assignment.SHARED_PREFERENCES};

        for (String fileName : files) {
            sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .clear()
                    .apply();
        }
    }
}


