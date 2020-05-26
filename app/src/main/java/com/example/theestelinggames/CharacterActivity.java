package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.theestelinggames.R;
import com.example.theestelinggames.SliderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String usernameKey = "USERNAME";
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


        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);
        ImageButton button3 = findViewById(R.id.button3);
        ImageButton button4 = findViewById(R.id.button4);
        ImageButton button5 = findViewById(R.id.button5);
        ImageButton button6 = findViewById(R.id.button6);
        ImageButton button7 = findViewById(R.id.button7);
        ImageButton button8 = findViewById(R.id.button8);
        ImageButton button9 = findViewById(R.id.button9);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Random random = new Random();
        int id = (random.nextInt(100000));
        SharedPreferences sharedPreferences = this.getSharedPreferences("PersonalDetails", this.MODE_PRIVATE);
        Log.i("Username", sharedPreferences.getString(usernameKey, "naamloos"));
        SharedPreferences.Editor editer = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.button1:
                Toast.makeText(this, "You chose Monkey!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Monkey" + id);
                break;
            case R.id.button2:
                Toast.makeText(this, "You chose Bear!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Bear" + id);
                break;
            case R.id.button3:
                Toast.makeText(this, "You chose Hare!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Hare" + id);
                break;
            case R.id.button4:
                Toast.makeText(this, "You chose Lion!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Lion" + id);
                break;
            case R.id.button5:
                Toast.makeText(this, "You chose Rhino!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Rhino" + id);
                break;
            case R.id.button6:
                Toast.makeText(this, "You chose Hippo!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Hippo" + id);
                break;
            case R.id.button7:
                Toast.makeText(this, "You chose Elephant!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Elephant" + id);
                break;
            case R.id.button8:
                Toast.makeText(this, "You chose Wolf!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Wolf" + id);
                break;
            case R.id.button9:
                Toast.makeText(this, "You chose Zebra!", Toast.LENGTH_SHORT).show();
                editer.putString(usernameKey, "Zebra" + id);
                break;
        }
        editer.apply();
    }
}
