package com.example.theestelinggames.mapdetail;

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

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.qrcode.QRActivity;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.navigation.NavigationView;

/**
 * Class in which the map is shown and makes it possible to look around on the map.
 */
public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOGTAG = MapActivity.class.getName();

    private DrawerLayout drawer;

    /**
     * Start method of the activity.
     * Creates the ToolBar and NavigationBar. Sets the map in the PhotoView.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SharedPreferences sharedPreferences = getSharedPreferences(
                CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = getString(sharedPreferences.getInt(
                CharacterActivity.USERNAMEID_KEY, -1))
                + " " + sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);

        Toolbar toolbar = findViewById(R.id.toolbarMAP);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_assignments).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_scoreboard).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_map).setChecked(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        PhotoView photoView = findViewById(R.id.photoViewMAP);
        photoView.setImageResource(R.drawable.efteling_map);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        Log.d(LOGTAG, "onBackPressed()");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getInt(
                    CharacterActivity.ID_KEY, -1) == -1) {
                super.onBackPressed();
            } else {
                Intent intent = new Intent(this, AssignmentListActivity.class);
                startActivity(intent);
            }
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
                return true;
            case R.id.nav_assignments:
                intent = new Intent(this, AssignmentListActivity.class);
                break;
            case R.id.nav_scoreboard:
                intent = new Intent(this, ScoreboardListActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(
                        CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
                String clientID = getString(sharedPreferences.getInt(
                        CharacterActivity.USERNAMEID_KEY, -1)) + " " +
                        sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);
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
