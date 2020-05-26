package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

        ArrayList<Assignment> assignments = new ArrayList<>(Arrays.asList(Assignment.getStaticAssignments()));

        RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        AssignmentAdapter minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetail.class);
        intent.putExtra(ItemDetail.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }
}
