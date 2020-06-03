package com.example.theestelinggames.assignmentlist;

import android.content.SharedPreferences;

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

    public static Assignment[] getStaticAssignments() {
        return staticAssignments;
    }

    public static Assignment getStaticAssignment(int id) {
        return staticAssignments[id];
    }

    //doesnt work
//    public void saveData() {
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(getName(), getName());
//        editor.putBoolean(getName() + "isCompleted", isCompleted());
//        editor.apply();
//
//    }

    //doesnt work
//    public void loadData() {
//
//        name = sharedPreferences.getString(getName(), "No name");
//        isCompleted = sharedPreferences.getBoolean(getName() + "isCompleted", false);
//        Log.i("LOAD", name + " " + isCompleted);
//
//    }

    //doesnt work
//    public void setSharedPreferences(Context context) {
//        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
//
//    }


}
