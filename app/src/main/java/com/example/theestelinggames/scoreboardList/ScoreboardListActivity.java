package com.example.theestelinggames.scoreboardList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.ItemDetailActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mqttconnection.MQTTConnection;
import com.example.theestelinggames.util.OnItemClickListener;

//import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;

public class ScoreboardListActivity extends AppCompatActivity implements OnItemClickListener {

    //MqttAndroidClient client;

    ArrayList<Scoreboard> scoreboard;

    ScoreboardAdapter scoreboardAdapter;

    MQTTConnection mqttConnectionReceive;

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
//        String[] string = clientID.split("(?<=\\D)(?=\\d)");
//        String animalName = string[0];
//        int id = Integer.parseInt(string[1]);

        //YOU CAN CONNECT TO THE SAME MQTT SERVER IF YOU HAVE THE SAME CLIENTID. SO YOU CAN ALWAYS MAKE NEW MQTTCONNECTIONS IF YOU FILL IN THE SAME CLIENTID.
        mqttConnectionReceive = MQTTConnection.newMQTTConnection(this,clientID+"IN");
        mqttConnectionReceive.connectAndListen(this);
    }

    public void update(){
        scoreboardAdapter.notifyDataSetChanged();
    }

    public ArrayList<Scoreboard> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(ArrayList<Scoreboard> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void addScore(String score){
        scoreboard.add(new Scoreboard(score));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(Scoreboard.SCOREBOARD_ID, clickedPosition);
        startActivity(intent);
    }
}
