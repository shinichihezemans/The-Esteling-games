package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ItemDetail extends AppCompatActivity {

    public static final String ASSIGNMENT_ID = "AssignmentID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_item_detail);
    }
}
