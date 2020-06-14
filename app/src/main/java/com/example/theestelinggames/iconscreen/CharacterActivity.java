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

    public static final String USERNAMEID_KEY = "usernameId";
    public static final String ID_KEY = "id";

    public static final String USERCREDENTIALS = "UserCredentials";

    private ViewPager2 viewPager2;
    private List<SliderItem> sliderItems;

    /**
     * This method is used to get a viewpager function for the character screen.
     * Because of this method its possible to slide throught the characters en see their
     * personal png file and name.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    /**
     * This method is used to save the character that is clicked by the user, this method also
     * generates a random int that we use as an id to give the user a special name so the user knows
     * where he/she placed on the leaderboard by finding this special name. this method also starts
     * the next activity.
     * @param clickedPosition
     */
    @Override
    public void onItemClick(int clickedPosition) {

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
