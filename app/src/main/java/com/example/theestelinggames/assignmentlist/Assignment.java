package com.example.theestelinggames.assignmentlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.theestelinggames.R;

import java.util.Arrays;
import java.util.Collections;

public class Assignment implements Comparable<Assignment> {

    private static final String LOGTAG = "assingment";

    private String name;
    private int attempts;
    private int score;
    private int imageResourceId;
    private int information;
    private int lineLength;

    //doesnt work
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES = "Assignment";
    private static final String SAVED_KEY = "saved";
    private static final String ATTEMPTS_KEY = "attempts";
    private static final String SCORE_KEY = "score";
    private static final String LINE_KEY = "lineLength";

    public Assignment(String name, int attempts, int score, int imageResourceId, int information) {
        sharedPreferences = null;

        this.name = name;
        this.attempts = attempts;
        this.score = score;
        this.imageResourceId = imageResourceId;
        this.information = information;
        this.lineLength = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getInformation() {
        return information;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    public int getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public static final Assignment[] staticAssignments = {
            new Assignment("Johan en de Eenhoorn", 1, 0, R.drawable.johan_en_de_eenhorn, R.string.JohanInformation),
            new Assignment("Cobra", 0, 0, R.drawable.cobra, R.string.CobraInformation),
            new Assignment("De zwevende Belg", 2, 0, R.drawable.de_zwevende_belg, R.string.ZwevendeBelgInformation),
            new Assignment("Droomreis", 3, 0, R.drawable.droomreis, R.string.DroomReisInformation)
    };

    public static Assignment[] getAssignments(Context context) {
        Assignment[] assignments = staticAssignments;
        Arrays.sort(assignments);
        for (Assignment assignment : assignments) {
            assignment.setSharedPreferences(context);
        }
        return assignments;
    }

    public static Assignment getAssignment(Context context, int pos) {
        Assignment assignment = staticAssignments[pos];
        assignment.setSharedPreferences(context);
        return assignment;
    }


    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getName() + SAVED_KEY, true);
        editor.putInt(getName() + ATTEMPTS_KEY, this.attempts);
        editor.putInt(getName() + SCORE_KEY, this.score);
        editor.putInt(getName() + LINE_KEY, this.lineLength);
        editor.commit();
        Log.i(LOGTAG + " saving", "saved " + getName());
    }

    public void syncWithPreferences() {
        if (sharedPreferences.getBoolean(getName() + SAVED_KEY, false)) {
            this.attempts = sharedPreferences.getInt(getName() + ATTEMPTS_KEY, -1);
//            Log.i(LOGTAG + "sync", "synced " + getName() + " attempts is " + this.attempts);
            this.score = sharedPreferences.getInt(getName() + SCORE_KEY, -1);
            this.lineLength = sharedPreferences.getInt(getName() + LINE_KEY, -1);
//            Log.i(LOGTAG + "sync", "synced " + getName() + " score is " + this.score);
//            Log.i(LOGTAG + "sync", "synced " + getName());
        } else {
//            Log.i(LOGTAG + "sync", "not synced " + getName());
        }
    }

    public void setSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
//        Log.i("sharedprefernces keys", String.valueOf(sharedPreferences.getAll().keySet()));
        syncWithPreferences();
    }

    @Override
    public int compareTo(Assignment o) {
        return this.lineLength - o.lineLength;
    }
}
