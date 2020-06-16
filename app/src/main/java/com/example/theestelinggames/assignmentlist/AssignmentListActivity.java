package com.example.theestelinggames.assignmentlist;

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
import com.example.theestelinggames.assignmentdetail.AssignmentDetailActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mapdetail.MapActivity;
import com.example.theestelinggames.qrcode.QRActivity;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;
import com.example.theestelinggames.util.OnItemClickListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class which displays all the available MiniGames, the users HighScore and the amount of attempts
 * left for the day.
 */
public class AssignmentListActivity extends AppCompatActivity implements OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String LOGTAG = AssignmentListActivity.class.getName();

    private ArrayList<Assignment> assignments;
    private AssignmentAdapter minigamesAdapter;
    private String clientID;
    private DrawerLayout drawer;

    /**
     * Start method of the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

        //set clientID for mqtt
        SharedPreferences sharedPreferences = getSharedPreferences(
                CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String animalName = getString(sharedPreferences.getInt(
                CharacterActivity.USERNAMEID_KEY, 0));
        int id = sharedPreferences.getInt(CharacterActivity.ID_KEY, 0);
        clientID = getString(sharedPreferences.getInt(CharacterActivity.USERNAMEID_KEY, 0))
                + " " + sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_assignments).setChecked(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);

        Toolbar toolbar = findViewById(R.id.toolbarOL);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //RecyclerView setup.
        assignments = new ArrayList<>(Arrays.asList(Assignment.getAssignments(this)));

        final RecyclerView minigamesRecyclerView = findViewById(R.id.minigamesRecyclerView);
        minigamesRecyclerView.setHasFixedSize(true);
        minigamesAdapter = new AssignmentAdapter(
                this, assignments, this);
        minigamesRecyclerView.setAdapter(minigamesAdapter);
        minigamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //To send message object to server
        MQTTConnection mqttConnectionSend = new MQTTConnection(
                this, clientID + "OUT");
        mqttConnectionSend.connectOUT(new Message(id, animalName));
    }

    /**
     * Called after the activity is visible to the user.
     * This is an indicator that the activity became active and ready to receive input.
     * It is on top of an activity stack and visible to user.
     */
    @Override
    protected void onResume() {
        Log.d(LOGTAG, "onResume()");

        super.onResume();
        //to sync everything
        assignments.clear();
        assignments.addAll(Arrays.asList(Assignment.getAssignments(this)));
        minigamesAdapter.notifyDataSetChanged();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        Log.d(LOGTAG, "onBackPressed()");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //close drawer when open
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).
                    getInt(CharacterActivity.ID_KEY, -1) == -1) {
                //if character hasn't been chosen yet
                super.onBackPressed();
            } else {
                //return to home screen
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }


    /**
     * Places the data of the assignment and the clickPosition into the intent and launches
     * the assignmentDetailActivity class.
     *
     * @param clickedPosition The position of the clicked view.
     */
    @Override
    public void onItemClick(int clickedPosition) {
        Log.d(LOGTAG, "onItemClick()");

        Intent intent = new Intent(this, AssignmentDetailActivity.class);
        intent.putExtra(AssignmentDetailActivity.ASSIGNMENT_ID, clickedPosition);
        startActivity(intent);
    }

    /**
     * Method is called when the activity is no longer visible to the user.
     */
    @Override
    protected void onStop() {
        Log.d(LOGTAG, "onStop()");

        super.onStop();
        saveSettings();
    }

    /**
     * Saves all assignment data.
     */
    public void saveSettings() {
        Log.d(LOGTAG, "saveSettings()");

        for (Assignment assignment : assignments) {
            assignment.saveData();
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param menuItem The selected item.
     * @return True to display the item as the selected item.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d(LOGTAG, "onNavigationItemSelected()");

        //Check where the user wants to go.
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_map:
                intent = new Intent(this, MapActivity.class);
                break;
            case R.id.nav_assignments:
                return true;
            case R.id.nav_scoreboard:
                intent = new Intent(this, ScoreboardListActivity.class);
                MQTTConnection mqttConnectionSend = new MQTTConnection(
                        this, clientID + "OUT");
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
