package com.example.theestelinggames.assignmentlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.theestelinggames.R;

import java.util.Arrays;

public class Assignment implements Comparable<Assignment> {

    private static final String LOGTAG = Assignment.class.getName();

    public static final String SHARED_PREFERENCES = "Assignment";
    private static final String SAVED_KEY = "saved";
    private static final String ATTEMPTS_KEY = "attempts";
    private static final String SCORE_KEY = "score";
    private static final String LINE_KEY = "lineLength";

    private String name;
    private int attempts;
    private int score;
    private int imageResourceId;
    private int information;
    private int lineLength;
    private SharedPreferences sharedPreferences;

    /**
     * Basic constructor of Assignment.
     *
     * @param name            The name of the assignment.
     * @param attempts        How many attempts the user has made to complete the assignment.
     * @param score           The total score of the assignment.
     * @param imageResourceId The image id which is used to display the image of the assignment
     *                        location.
     * @param information     The basic information of the assignment.
     */
    private Assignment(String name, int attempts, int score, int imageResourceId, int information) {
        sharedPreferences = null;
        this.name = name;
        this.attempts = attempts;
        this.score = score;
        this.imageResourceId = imageResourceId;
        this.information = information;
        this.lineLength = 0;
    }

    /**
     * Getter for the assignment name.
     *
     * @return The variable name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the amount of attempts.
     *
     * @return The variable attempts.
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Set attempts if value is between 0 and 3.
     *
     * @param attempts The new amount of attempts.
     */
    public void setAttempts(int attempts) {
        if (attempts <= 3 && attempts >= 0) {
            this.attempts = attempts;
        }
    }

    /**
     * Getter for the image resource ID.
     *
     * @return The variable imageResourceId.
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * Getter for the score.
     *
     * @return The variable score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score if given score is higher.
     *
     * @param score The new score.
     * @return True if the score was higher.
     */
    public boolean setHighScore(int score) {
        if (this.score < score) {
            this.score = score;
            return true;
        }
        return false;
    }

    /**
     * Getter for the information text.
     *
     * @return The variable information.
     */
    public int getInformation() {
        return information;
    }

    /**
     * Getter for the line length.
     *
     * @return The variable lineLength.
     */
    int getLineLength() {
        return lineLength;
    }

    /**
     * Setter for the variable line length.
     *
     * @param lineLength The new line length.
     */
    void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    /**
     * Hardcoded assignments.
     */
    private static final Assignment[] staticAssignments = {
            new Assignment("Johan en de Eenhoorn", 0, 0,
                    R.drawable.johan_en_de_eenhorn, R.string.JohanInformation),
            new Assignment("Cobra", 0, 0,
                    R.drawable.cobra, R.string.CobraInformation),
            new Assignment("De zwevende Belg", 0, 0,
                    R.drawable.de_zwevende_belg, R.string.ZwevendeBelgInformation),
            new Assignment("Droomreis", 0, 0,
                    R.drawable.droomreis, R.string.DroomReisInformation)
    };

    /**
     * Gets assignments sorted.
     *
     * @param context To set sharedpreferences.
     * @return Sorted assignments.
     */
    static Assignment[] getAssignments(Context context) {
        Assignment[] assignments = staticAssignments;
        Arrays.sort(assignments);
        for (Assignment assignment : assignments) {
            assignment.setSharedPreferences(context);
        }
        return assignments;
    }

    /**
     * Gets particular assignment (sorted).
     *
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
     * Set sharedPreferences with the context.
     */
    private void setSharedPreferences(Context context) {
        Log.d(LOGTAG, "setSharedPreferences()");

        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        syncWithPreferences();
    }

    /**
     * Comparator to see if the line length is bigger, equal or smaller than the compared object.
     *
     * @param o Object which will be used to compared to the current line length.
     * @return An integer between -1 and 1 depending on if the compared object bigger, equal or
     * smaller than the line length.
     */
    @Override
    public int compareTo(Assignment o) {
        return this.lineLength - o.lineLength;
    }
}
