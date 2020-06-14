package com.example.theestelinggames.assignmentlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.theestelinggames.R;

import java.util.Arrays;

public class Assignment implements Comparable<Assignment> {

    private static final String LOGTAG = "assingment";

    private String name;
    private int attempts;
    private int score;
    private int imageResourceId;
    private int information;
    private int lineLength;

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES = "Assignment";
    private static final String SAVED_KEY = "saved";
    private static final String ATTEMPTS_KEY = "attempts";
    private static final String SCORE_KEY = "score";
    private static final String LINE_KEY = "lineLength";

    private Assignment(String name, int attempts, int score, int imageResourceId, int information) {
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

    /**
     * Set attempts if value is between 0 and 3.
     * @param attempts value
     */
    public void setAttempts(int attempts) {
        if(attempts <= 3 && attempts >= 0) {
            this.attempts = attempts;
        }
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets score if given score is higher.
     * @param score new score.
     * @return if score was higher.
     */
    public boolean setHighScore(int score){
        if(this.score < score){
            this.score = score;
            return true;
        }
        return false;
    }

    public int getInformation() {
        return information;
    }

    int getLineLength() {
        return lineLength;
    }

    void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    /**
     * Hardcoded assignments.
     */
    private static final Assignment[] staticAssignments = {
            new Assignment("Johan en de Eenhoorn", 0, 0, R.drawable.johan_en_de_eenhorn, R.string.JohanInformation),
            new Assignment("Cobra", 0, 0, R.drawable.cobra, R.string.CobraInformation),
            new Assignment("De zwevende Belg", 0, 0, R.drawable.de_zwevende_belg, R.string.ZwevendeBelgInformation),
            new Assignment("Droomreis", 0, 0, R.drawable.droomreis, R.string.DroomReisInformation)
    };

    /**
     * Gets assignments sorted.
     * @param context to set sharedpreferences.
     * @return sorted assignments.
     */
    public static Assignment[] getAssignments(Context context) {
        Assignment[] assignments = staticAssignments;
        Arrays.sort(assignments);
        for (Assignment assignment : assignments) {
            assignment.setSharedPreferences(context);
        }
        return assignments;
    }

    /**
     * Gets particular assignment (sorted).
     * @param context to set sharedpreferences.
     * @return particular (sorted) assignment.
     */
    public static Assignment getAssignment(Context context, int pos) {
        Assignment assignment = staticAssignments[pos];
        assignment.setSharedPreferences(context);
        return assignment;
    }

    /**
     * Saves data in sharedPreferences (blocking call).
     */
    public void saveData() {
        Log.d(LOGTAG, "saveData()");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getName() + SAVED_KEY, true);
        editor.putInt(getName() + ATTEMPTS_KEY, this.attempts);
        editor.putInt(getName() + SCORE_KEY, this.score);
        editor.putInt(getName() + LINE_KEY, this.lineLength);
        editor.apply();
        Log.i(LOGTAG + " saving", "saved " + getName());
    }

    /**
     * Set attributes to the values is sharedPreferences.
     */
    public void syncWithPreferences() {
        Log.d(LOGTAG, "saveData()");

        if (sharedPreferences.getBoolean(getName() + SAVED_KEY, false)) {
            this.attempts = sharedPreferences.getInt(getName() + ATTEMPTS_KEY, -1);
            this.score = sharedPreferences.getInt(getName() + SCORE_KEY, -1);
            this.lineLength = sharedPreferences.getInt(getName() + LINE_KEY, -1);
        }
    }

    /**
     * Set sharedPreferences with the context
     */
    public void setSharedPreferences(Context context) {
        Log.d(LOGTAG, "setSharedPreferences()");

        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        syncWithPreferences();
    }

    @Override
    public int compareTo(Assignment o) {
        return this.lineLength - o.lineLength;
    }
}
