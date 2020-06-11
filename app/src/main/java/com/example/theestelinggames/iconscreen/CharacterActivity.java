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
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
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

        sliderItems = new ArrayList<>();

        Collections.addAll(sliderItems,
                new SliderItem(getString(R.string.Monkey), R.drawable.aaptrans),
                new SliderItem(getString(R.string.Bear), R.drawable.beertrans),
                new SliderItem(getString(R.string.Hare), R.drawable.haastrans),
                new SliderItem(getString(R.string.Lion), R.drawable.leeuwtrans),
                new SliderItem(getString(R.string.Rhino), R.drawable.neushoorntrans),
                new SliderItem(getString(R.string.Hippo), R.drawable.nijlpaardtrans),
                new SliderItem(getString(R.string.Elephant), R.drawable.olifanttrans),
                new SliderItem(getString(R.string.Wolf), R.drawable.wolftrans),
                new SliderItem(getString(R.string.Zebra), R.drawable.zebratrans));

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

        final Intent intent = new Intent(this, AssignmentListActivity.class);

        startActivity(intent);
    }

}
