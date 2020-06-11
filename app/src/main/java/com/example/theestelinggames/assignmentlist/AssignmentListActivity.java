package com.example.theestelinggames.assignmentlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import com.example.theestelinggames.assignmentdetail.ItemDetailActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;
import com.example.theestelinggames.util.OnItemClickListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;


public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGTAG = AssignmentListActivity.class.getName();

    public static final String USERSENT = "HasSent";

    public static final String playerSent = "playerSent";

    private boolean hasSent = false;

    ArrayList<Assignment> assignments;

    AssignmentAdapter minigamesAdapter;

    String clientID;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);


        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);
        String[] string = clientID.split("(?<=\\D)(?=\\d)");
        String animalName = string[0];
        int id = Integer.parseInt(string[1]);

        Toolbar toolbar = findViewById(R.id.toolbarOL);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //hardcoded stuff
        navigationView.getMenu().findItem(R.id.nav_assignments).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_scoreboard).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(false);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);

        assignments = new ArrayList<>(Arrays.asList(Assignment.getAssignments(this)));

        final RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        minigamesRecyclerView.setHasFixedSize(true);
        minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset >= 0.5)
                    minigamesAdapter.setColor(Color.TRANSPARENT);
                if (slideOffset <= 0.5) {
                    minigamesAdapter.setColor(Color.WHITE);
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        //To send message player object to server
        MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID + "OUT");

        mqttConnectionSend.connectOUT(new Message(id, animalName));

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
                super.onBackPressed();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_assignments:
                return true;

            case R.id.nav_scoreboard:
                intent = new Intent(this, ScoreboardListActivity.class);
                MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID + "OUT");
                mqttConnectionSend.connectOUT(new Message("get Scoreboard"));
                break;
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
