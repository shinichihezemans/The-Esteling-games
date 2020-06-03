package com.example.theestelinggames.assignmentlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.theestelinggames.ItemDetail;
import com.example.theestelinggames.OnItemClickListener;
import com.example.theestelinggames.R;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener {

    ArrayList<Assignment> assignments;

    AssignmentAdapter minigamesAdapter;

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

        Intent intent = new Intent(this, ItemDetail.class);
        intent.putExtra(ItemDetail.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }

    //doesnt work
    @Override
    protected void onDestroy() {

        super.onDestroy();

//        saveSettings();

    }

    public void printList(){
        for (Assignment assignment :
                assignments) {
            Log.i("LISTLIST",assignment.getName() + " " + assignment.isCompleted() );
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
