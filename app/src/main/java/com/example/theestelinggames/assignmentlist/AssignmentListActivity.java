package com.example.theestelinggames.assignmentlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.ItemDetailActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mqttconnection.MQTTConnection;
import com.example.theestelinggames.mqttconnection.Message;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String LOGTAG = AssignmentListActivity.class.getName();

    ArrayList<Assignment> assignments;

    AssignmentAdapter minigamesAdapter;

    MQTTConnection mqttConnection;

    //uncheck clickable in assignment overview item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

        assignments = new ArrayList<>(Arrays.asList(Assignment.getStaticAssignments()));

        //doesnt work
//        for (Assignment assignment: assignments) {
//            assignment.setSharedPreferences(this);
//            assignment.saveData();
//            assignment.loadData();
//        }

        RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int clickedPosition) {

        printList();

//        saveSettings();

        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }

    public void navigateScoreboard(View view) {
            Intent intent = new Intent(this, ScoreboardListActivity.class);
            startActivity(intent);
    }

    //doesnt work
    @Override
    protected void onDestroy() {

        super.onDestroy();

//        saveSettings();

    }

    public void printList() {
        for (Assignment assignment :
                assignments) {
            Log.i(LOGTAG, assignment.getName() + " - status: " + assignment.isCompleted());
        }
    }

    //doesnt work
//    public void saveSettings(){
//
//        for (Assignment assignment: assignments) {
//
//            //Name
//            TextView minigameName = findViewById(R.id.minigameName);
//            assignment.setName(minigameName.getText().toString());
//            Log.i("SaveSettings", assignment.getName());
//
////            //attempts
////            TextView minigameAttempts = findViewById(R.id.minigameAttempts);
////            String attemptsText = minigameAttempts.getText().toString();
////            String[] splitString = attemptsText.split("/");
////            int attemptsINT = Integer.parseInt(splitString[0]);
////            assignment.setAttempts(attemptsINT);
////            Log.i("Save", String.valueOf(assignment.getAttempts()));
//
//            //status
//            CheckBox checkBox = findViewById(R.id.checkBox);
//            boolean isCompleted = checkBox.isChecked();
//            assignment.setCompleted(isCompleted);
//            Log.i("SaveSettings",String.valueOf(assignment.isCompleted()));
//
//            assignment.saveData();
//        }
//    }
}
