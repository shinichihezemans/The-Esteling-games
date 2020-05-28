package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOGTAG = CharacterActivity.class.getName();

    public static final String usernameKey = "USERNAME";

    public static final String USERCREDENTIALS = "UserCredentials";

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        viewPager2 = findViewById(R.id.viewPager);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.aap));
        sliderItems.add(new SliderItem(R.drawable.beer));
        sliderItems.add(new SliderItem(R.drawable.haas));
        sliderItems.add(new SliderItem(R.drawable.leeuw));
        sliderItems.add(new SliderItem(R.drawable.neushoorn));
        sliderItems.add(new SliderItem(R.drawable.nijlpaard));
        sliderItems.add(new SliderItem(R.drawable.olifant));
        sliderItems.add(new SliderItem(R.drawable.wolf));
        sliderItems.add(new SliderItem(R.drawable.zebra));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        ImageButton buttonAap = findViewById(R.id.buttonAap);
        ImageButton buttonBeer = findViewById(R.id.buttonBeer);
        ImageButton buttonHaas = findViewById(R.id.buttonHaas);
        ImageButton buttonLeeuw = findViewById(R.id.buttonLeeuw);
        ImageButton buttonNeushorn = findViewById(R.id.buttonNeushorn);
        ImageButton buttonNijlpaard = findViewById(R.id.buttonNijlpaard);
        ImageButton buttonOlifant = findViewById(R.id.buttonOlifant);
        ImageButton buttonWolf = findViewById(R.id.buttonWolf);
        ImageButton buttonZebra = findViewById(R.id.buttonZebra);

        buttonAap.setOnClickListener(this);
        buttonBeer.setOnClickListener(this);
        buttonHaas.setOnClickListener(this);
        buttonLeeuw.setOnClickListener(this);
        buttonNeushorn.setOnClickListener(this);
        buttonNijlpaard.setOnClickListener(this);
        buttonOlifant.setOnClickListener(this);
        buttonWolf.setOnClickListener(this);
        buttonZebra.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Random random = new Random();
        int id = (random.nextInt(100000));
        SharedPreferences sharedPreferences = this.getSharedPreferences(USERCREDENTIALS, this.MODE_PRIVATE);
        Log.i("Username", sharedPreferences.getString(usernameKey, "nameless"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String animalName = "";
        switch (v.getId()) {
            case R.id.buttonAap:
                animalName = "Monkey";
                break;
            case R.id.buttonBeer:
                animalName = "Bear";
                break;
            case R.id.buttonHaas:
                animalName = "Hare";
                break;
            case R.id.buttonLeeuw:
                animalName = "Lion";
                break;
            case R.id.buttonNeushorn:
                animalName = "Rhino";
                break;
            case R.id.buttonNijlpaard:
                animalName = "Hippo";
                break;
            case R.id.buttonOlifant:
                animalName = "Elephant";
                break;
            case R.id.buttonWolf:
                animalName = "Wolf";
                break;
            case R.id.buttonZebra:
                animalName = "Zebra";
                break;
            default:
                Log.d(LOGTAG,"Invalid button");
        }
        Toast.makeText(this, animalName + " has been chosen!", Toast.LENGTH_SHORT).show();
        editor.putString(usernameKey, animalName + id);
        editor.apply();

        final Intent intent = new Intent(this, AssignmentListActivity.class);
        startActivity(intent);

    }
}
