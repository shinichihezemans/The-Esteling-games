package com.example.theestelinggames.scoreboardList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.QRcode.QRActivity;
import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.OnItemClickListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

//import org.eclipse.paho.android.service.MqttAndroidClient;

public class ScoreboardListActivity extends AppCompatActivity implements OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    //MqttAndroidClient client;

    ArrayList<Scoreboard> scoreboard;

    ScoreboardAdapter scoreboardAdapter;

    MQTTConnection mqttConnectionReceive;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);
        String[] string = clientID.split("(?<=\\D)(?=\\d)");
        String animalName = string[0];

        Toolbar toolbar = findViewById(R.id.toolbarHS);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_assignments).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_scoreboard).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(false);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);
//        getIcon(item, animalName);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        scoreboard = new ArrayList<>(10);
        RecyclerView scoreboardRecyclerView = findViewById(R.id.scoreboardRecyclerView);
        scoreboardAdapter = new ScoreboardAdapter(
                this, scoreboard);
        scoreboardRecyclerView.setAdapter(scoreboardAdapter);
        scoreboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Receives scoreboard
        mqttConnectionReceive = MQTTConnection.newMQTTConnection(this, clientID + "IN");
        mqttConnectionReceive.setScoreboardListActivity(this);
        mqttConnectionReceive.connectIN();
    }

    public void clear(){

        scoreboard.clear();
    }

    public ArrayList<Scoreboard> getScoreboard() {
        return scoreboard;
    }

    //    public void getIcon(MenuItem item, String animalName) {
//
//        switch (animalName) {
//            case "Monkey":
//                item.setIcon(R.drawable.aaptrans);
//                break;
//            case "Bear":
//                item.setIcon(R.drawable.beertrans);
//                break;
//            case "Hare":
//                item.setIcon(R.drawable.haastrans);
//                break;
//            case "Lion":
//                item.setIcon(R.drawable.leeuwtrans);
//                break;
//            case "Rhino":
//                item.setIcon(R.drawable.neushoorntrans);
//                break;
//            case "Hippo":
//                item.setIcon(R.drawable.nijlpaardtrans);
//                break;
//            case "Elephant":
//                item.setIcon(R.drawable.olifanttrans);
//                break;
//            case "Wolf":
//                item.setIcon(R.drawable.wolftrans);
//                break;
//            case "Zebra":
//                item.setIcon(R.drawable.zebratrans);
//                break;
//            default:
//
//        }
//
//    }

    public void update() {
        scoreboardAdapter.notifyDataSetChanged();
    }


    public void addScore(String username, int id) {
        if(scoreboard.size()>=10) {
            scoreboard.clear();
        }
        scoreboard.add(new Scoreboard(username, id));
    }

    @Override
    public void onItemClick(int clickedPosition) {

    }

    @Override
    public void onBackPressed() {
        mqttConnectionReceive.closeConnection();
        mqttConnectionReceive.setScoreboardListActivity(null);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
                super.onBackPressed();
            } else {
                Intent intent = new Intent(this, AssignmentListActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_assignments:
                intent = new Intent(this, AssignmentListActivity.class);
                break;
            case R.id.nav_scoreboard:
                return true;

            case R.id.nav_qr:
                intent = new Intent(this, QRActivity.class);
                break;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }
}