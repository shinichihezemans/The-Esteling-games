package com.example.theestelinggames.assignmentlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.theestelinggames.R;

public class Assignment {

    private String name;
    private int attempts;
    private boolean isCompleted;
    private int score;
    private int imageResourceId;
    private int information;

    //doesnt work
    private SharedPreferences sharedPreferences;
    private final String SHARED_PREFERENCES = "Assignment";
    private static final String SAVED_KEY = "saved";
    private static final String ATTEMPTS_KEY = "attempts";
    private static final String COMPLETED_KEY = "completed";
    private static final String SCORE_KEY = "score";

    public Assignment(String name, int attempts, boolean isCompleted, int score, int imageResourceId, int information) {
        sharedPreferences = null;

        this.name = name;
        this.isCompleted = isCompleted;
        this.attempts = attempts;
        this.score = score;
        this.imageResourceId = imageResourceId;
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean status) {
        isCompleted = status;
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

    private static final Assignment[] staticAssignments = {
            new Assignment("Johan en de Eenhoorn", 1, false, 0, R.drawable.johan_en_de_eenhorn, R.string.JohanInformation),
            new Assignment("Cobra", 0, true, 0, R.drawable.cobra, R.string.CobraInformation),
            new Assignment("De zwevende Belg", 2, false, 0, R.drawable.de_zwevende_belg, R.string.ZwevendeBelgInformation),
            new Assignment("Droomreis", 3, false, 0, R.drawable.droomreis, R.string.DroomReisInformation)
    };
//
//    public static Assignment[] getStaticAssignments() {
//        return staticAssignments;
//    }
//
//    public static Assignment getStaticAssignment(int id) {
//        return staticAssignments[id];
//    }

    public static Assignment[] getAssignments(Context context) {
        Assignment[] assignments = staticAssignments.clone();
        for (Assignment assignment : assignments) {
            assignment.setSharedPreferences(context);
            assignment.syncWithPrefernces();
        }
        return assignments;
    }

    public static Assignment getAssignment(Context context, int pos) {
        Assignment assignment = staticAssignments[pos];
        assignment.setSharedPreferences(context);
        assignment.syncWithPrefernces();
        return assignment;
    }


    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getName() + SAVED_KEY, true);
        editor.putInt(getName() + ATTEMPTS_KEY, this.attempts);
        editor.putBoolean(getName() + COMPLETED_KEY, this.isCompleted);
        editor.putInt(getName() + SCORE_KEY, this.score);
        editor.apply();

    }

    public void syncWithPrefernces() {
        if (sharedPreferences.getBoolean(getName() + SAVED_KEY, false)) {
            this.attempts = sharedPreferences.getInt(getName() + ATTEMPTS_KEY, -1);
            this.isCompleted = sharedPreferences.getBoolean(getName() + COMPLETED_KEY, false);
            this.score = sharedPreferences.getInt(getName() + SCORE_KEY, -1);
        }
    }

    public void setSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Log.i("TEMP", String.valueOf(sharedPreferences.getAll().keySet()));
    }


}
