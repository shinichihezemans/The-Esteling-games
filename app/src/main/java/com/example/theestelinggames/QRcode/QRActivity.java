package com.example.theestelinggames.QRcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;
import com.example.theestelinggames.util.MQTTConnection;
import com.example.theestelinggames.util.Message;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USERCREDENTIALS = "UserCredentials";
    private static final String LOGTAG = "QRActivity";

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);
        String[] string = clientID.split("(?<=\\D)(?=\\d)");
        String animalName = string[0];

        Toolbar toolbar = findViewById(R.id.toolbarQR);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_assignments).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_scoreboard).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.navUserID);
        item.setTitle(clientID);
//        getIcon(item,animalName);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ImageView imageView = (ImageView) findViewById(R.id.qr_imageView);



        if (clientID != null) {
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
                BitMatrix bitMatrix = multiFormatWriter.encode(clientID, BarcodeFormat.QR_CODE, size, size);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
//
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE).getString(CharacterActivity.usernameKey, "no name").equals("no name")) {
                super.onBackPressed();
            } else {
                Intent intent = new Intent(this,AssignmentListActivity.class);
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
                intent = new Intent(this, ScoreboardListActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
                String clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);
                MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID + "OUT");
                mqttConnectionSend.connectOUT(new Message("get Scoreboard"));
                break;
            case R.id.nav_qr:
//                intent = new Intent(this, QRActivity.class);
                break;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }
}