package com.example.theestelinggames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setContentView(R.layout.assignment_overview);

        ArrayList<Assignment> assignments = new ArrayList<>();

        for (Assignment assignment :
                staticAssignments) {
         assignments.add(assignment);
        }

        Log.i("Info", String.valueOf(assignments.size()));

        RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        AssignmentAdapter minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private static final Assignment[] staticAssignments = {
            new Assignment("test1", 1,false),
            new Assignment("test2", 0,false),
            new Assignment("test3", 2,true),
            new Assignment("test4", 3,true)
    };

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetail.class);
        intent.putExtra(ItemDetail.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }



}


