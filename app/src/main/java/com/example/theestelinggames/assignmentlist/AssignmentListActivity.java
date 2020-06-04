package com.example.theestelinggames.assignmentlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.assignmentdetail.ItemDetailActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.Message;
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String LOGTAG = AssignmentListActivity.class.getName();

    ArrayList<Assignment> assignments;

    AssignmentAdapter minigamesAdapter;

    String clientID;
    MQTTConnection mqttConnectionSend= MQTTConnection.newMQTTConnection(this, clientID + "OUT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        assignments = new ArrayList<>(Arrays.asList(Assignment.getAssignments(this)));

        RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);
        String[] string = clientID.split("(?<=\\D)(?=\\d)");
        String animalName = string[0];
        int id = Integer.parseInt(string[1]);

        //To send message player object to server
        MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID + "OUT");

        mqttConnectionSend.connectOUT(new Message(id, animalName));
    }

    public void navigateScoreboard(View view) {
        Intent intent = new Intent(this, ScoreboardListActivity.class);

//        Requests scoreboard
        MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID + "OUT");
        mqttConnectionSend.connectOUT(new Message("get Scoreboard"));
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        assignments.clear();
        assignments.addAll(Arrays.asList(Assignment.getAssignments(this)));
        minigamesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ASSIGNMENT_ID, clickedPosition);
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
