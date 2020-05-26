package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetail extends AppCompatActivity {
    private static final String LOGTAG = ItemDetail.class.getName();

    public static final String ASSIGNMENT_ID = "AssignmentID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_item_detail);

        int id = getIntent().getExtras().getInt(ASSIGNMENT_ID);
        Log.d(LOGTAG, "onCreate called with EXTRA_ZODIAC_ID = " + id);

        Assignment assignment = Assignment.getStaticAssignment(id);
        Log.d(LOGTAG, "Assignment[id] = " + assignment.getName() + " " + assignment.getAttempts());

        TextView minigameName = (TextView) findViewById(R.id.minigameName);
        minigameName.setText(assignment.getName());

        TextView forecast = (TextView) findViewById(R.id.minigameIntroduction);
        forecast.setText("TODO");

//        ImageView attractionImage = (ImageView) findViewById();
//        attractionImage.setImageResource();
    }



}
