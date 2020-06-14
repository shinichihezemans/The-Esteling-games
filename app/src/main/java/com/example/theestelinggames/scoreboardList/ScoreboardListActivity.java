package com.example.theestelinggames.scoreboardList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mapdetail.MapActivity;
import com.example.theestelinggames.qrcode.QRActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ScoreboardListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOGTAG = ScoreboardListActivity.class.getName();

    private ArrayList<Scoreboard> scoreboard;
    private ScoreboardAdapter scoreboardAdapter;
    private MQTTConnection mqttConnectionReceive;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = getString(sharedPreferences.getInt(CharacterActivity.USERNAMEID_KEY, -1)) + " " + sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);

        Toolbar toolbar = findViewById(R.id.toolbarHS);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_scoreboard).setChecked(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);

        scoreboard = new ArrayList<>(10);
        final RecyclerView scoreboardRecyclerView = findViewById(R.id.scoreboardRecyclerView);
        scoreboardAdapter = new ScoreboardAdapter(
                this, scoreboard);
        scoreboardRecyclerView.setAdapter(scoreboardAdapter);
        scoreboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Receives scoreboard
        mqttConnectionReceive = MQTTConnection.newMQTTConnection(this, clientID + "IN");
        mqttConnectionReceive.setScoreboardListActivity(this);
        mqttConnectionReceive.connectIN();
    }

    public void clear() {
        Log.d(LOGTAG, "clear()");

        scoreboard.clear();
        scoreboardAdapter.notifyDataSetChanged();
    }

    public void addScore(String username, int id) {
        Log.d(LOGTAG, "addScore()");

        if (scoreboard.size() >= 10) {
            scoreboard.clear();
        }
        scoreboard.add(new Scoreboard(username, id));
        scoreboardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOGTAG, "onBackPressed()");

        mqttConnectionReceive.closeConnection();
        mqttConnectionReceive.setScoreboardListActivity(null);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getInt(CharacterActivity.ID_KEY, -1) == -1) {
                super.onBackPressed();
            } else {
                Intent intent = new Intent(this, AssignmentListActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d(LOGTAG, "onNavigationItemSelected()");

        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_map:
                intent = new Intent(this, MapActivity.class);
                break;
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