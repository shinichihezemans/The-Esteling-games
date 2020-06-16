package com.example.theestelinggames.qrcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.mapdetail.MapActivity;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USERCREDENTIALS = "UserCredentials";
    private static final String LOGTAG = "QRActivity";

    private DrawerLayout drawer;

    /**
     * Start method of the activity.
     * Creates the users QR code and displays it on the screen.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        SharedPreferences sharedPreferences = getSharedPreferences(
                CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = String.valueOf(sharedPreferences.getInt(
                CharacterActivity.ID_KEY, -1));

        Toolbar toolbar = findViewById(R.id.toolbarQR);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(clientID);
        if (m.find()) {
            clientID = m.group();
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        try {
            int size = displayMetrics.widthPixels - (displayMetrics.widthPixels / 5);
            BitMatrix bitMatrix = multiFormatWriter.encode(
                    clientID, BarcodeFormat.QR_CODE, size, size);
            BarcodeEncoderLite barcodeEncoderLite = new BarcodeEncoderLite();
            Bitmap bitmap = barcodeEncoderLite.createBitmap(bitMatrix);
            ImageView imageView = findViewById(R.id.qr_imageView);
            imageView.setImageBitmap(bitmap);
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        } catch (WriterException e) {
            e.printStackTrace();
        }
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
                intent = new Intent(this, MapActivity.class);
                break;
            case R.id.nav_assignments:
                intent = new Intent(this, AssignmentListActivity.class);
                break;
            case R.id.nav_scoreboard:
                intent = new Intent(this, ScoreboardListActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(
                        CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
                String clientID = getString(sharedPreferences.getInt(
                        CharacterActivity.USERNAMEID_KEY, -1))
                        + " " + sharedPreferences.getInt(CharacterActivity.ID_KEY, -1);
                MQTTConnection mqttConnectionSend = new MQTTConnection(
                        this, clientID + "OUT");
                mqttConnectionSend.connectOUT(new Message("get Scoreboard"));
                break;
            case R.id.nav_qr:
                return true;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }
}