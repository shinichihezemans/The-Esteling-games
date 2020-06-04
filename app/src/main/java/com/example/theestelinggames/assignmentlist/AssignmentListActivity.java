package com.example.theestelinggames.assignmentlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.theestelinggames.ItemDetail;
import com.example.theestelinggames.OnItemClickListener;
import com.example.theestelinggames.R;
import com.example.theestelinggames.iconscreen.CharacterActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener {

    ArrayList<Assignment> assignments;

    AssignmentAdapter minigamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

//        Log.i("sharedprefrences before", "" + getSharedPreferences(Assignment.SHARED_PREFERENCES, MODE_PRIVATE).getAll().keySet());

        assignments = new ArrayList<>(Arrays.asList(Assignment.getAssignments(this)));

        RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
            super.onBackPressed();
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetail.class);
        intent.putExtra(ItemDetail.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSettings();
    }


    public void saveSettings() {
        for (Assignment assignment : assignments) {
            assignment.saveData();
        }
    }
}
