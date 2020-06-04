package com.example.theestelinggames.scoreboardList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.ItemDetailActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mqttconnection.MQTTConnection;
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.ArrayList;

//import org.eclipse.paho.android.service.MqttAndroidClient;

public class ScoreboardListActivity extends AppCompatActivity implements OnItemClickListener {

    //MqttAndroidClient client;

    ArrayList<Scoreboard> scoreboard;

    ScoreboardAdapter scoreboardAdapter;

//    MQTTConnection mqttConnectionReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        scoreboard = new ArrayList<>(10);
        RecyclerView scoreboardRecyclerView = findViewById(R.id.scoreboardRecyclerView);
        scoreboardAdapter = new ScoreboardAdapter(
                this, scoreboard, this);
        scoreboardRecyclerView.setAdapter(scoreboardAdapter);
        scoreboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);

        //Receives scoreboard
        MQTTConnection mqttConnectionReceive = MQTTConnection.newMQTTConnection(this, clientID + "IN");
        mqttConnectionReceive.connectIN(this);
    }

    public void update() {
        scoreboardAdapter.notifyDataSetChanged();
    }


    public void addScore(String username, int id) {
        scoreboard.add(new Scoreboard(username, id));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(Scoreboard.SCOREBOARD_ID, clickedPosition);
        startActivity(intent);
    }
}
