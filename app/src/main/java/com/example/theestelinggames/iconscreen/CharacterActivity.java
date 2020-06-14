package com.example.theestelinggames.iconscreen;

import android.content.Intent;
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
    private static final String LOGTAG = CharacterActivity.class.getName();

    public static final String USERNAMEID_KEY = "usernameId";
    public static final String ID_KEY = "id";

    public static final String USERCREDENTIALS = "UserCredentials";

    private ViewPager2 viewPager2;
    private List<SliderItem> sliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG,"onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        sliderItems = new ArrayList<>();

        Collections.addAll(sliderItems,
                new SliderItem(R.string.Monkey, R.drawable.aaptrans),
                new SliderItem(R.string.Bear, R.drawable.beertrans),
                new SliderItem(R.string.Hare, R.drawable.haastrans),
                new SliderItem(R.string.Lion, R.drawable.leeuwtrans),
                new SliderItem(R.string.Rhino, R.drawable.neushoorntrans),
                new SliderItem(R.string.Hippo, R.drawable.nijlpaardtrans),
                new SliderItem(R.string.Elephant, R.drawable.olifanttrans),
                new SliderItem(R.string.Wolf, R.drawable.wolftrans),
                new SliderItem(R.string.Zebra, R.drawable.zebratrans));

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new SliderAdapter(sliderItems, this));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.d(LOGTAG,"onItemClick()");

        Random random = new Random();
        int id = (random.nextInt(100000));

        int animalNameId = sliderItems.get(viewPager2.getCurrentItem()).getIconName();

        String clientID = getString(animalNameId) + " " + id;
        Toast.makeText(this, clientID + " has been chosen!", Toast.LENGTH_SHORT).show();

        getSharedPreferences(USERCREDENTIALS, MODE_PRIVATE)
                .edit()
                .putInt(USERNAMEID_KEY, animalNameId)
                .putInt(ID_KEY, id)
                .apply();

        final Intent intent = new Intent(this, AssignmentListActivity.class);

        startActivity(intent);
    }

}
