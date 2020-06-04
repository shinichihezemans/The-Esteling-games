package com.example.theestelinggames.iconscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentListActivity;
import com.example.theestelinggames.mqttconnection.MQTTConnection;
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CharacterActivity extends AppCompatActivity implements OnItemClickListener {
    public static final String LOGTAG = CharacterActivity.class.getName();

    public static final String usernameKey = "USERNAME";

    public static final String USERCREDENTIALS = "UserCredentials";

    private ViewPager2 viewPager2;
    private List<SliderItem> sliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        sliderItems = new ArrayList<>(Arrays.asList(SliderItem.getStaticSliderItems()));

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new SliderAdapter(sliderItems, this));
    }

    @Override
    public void onItemClick(int clickedPosition) {

        Random random = new Random();
        int id = (random.nextInt(100000));
        SharedPreferences sharedPreferences = this.getSharedPreferences(USERCREDENTIALS, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String animalName = sliderItems.get(viewPager2.getCurrentItem()).getIconName();

        String clientID = animalName + id;
        Toast.makeText(this, clientID + " has been chosen!", Toast.LENGTH_SHORT).show();
        editor.putString(usernameKey, clientID);
        editor.apply();
        Log.i("Username", sharedPreferences.getString(usernameKey, "nameless"));


        //To send message player object to server
        MQTTConnection mqttConnectionSend = MQTTConnection.newMQTTConnection(this, clientID+"OUT");
        mqttConnectionSend.connectOUT(id, animalName);

//        Log.i(LOGTAG, String.valueOf(sharedPreferences.getAll().keySet()));

        final Intent intent = new Intent(this, AssignmentListActivity.class);
        //not sure
//        intent.putExtra(MQTTConnection.ID,mqttConnection);

        startActivity(intent);
    }
}
