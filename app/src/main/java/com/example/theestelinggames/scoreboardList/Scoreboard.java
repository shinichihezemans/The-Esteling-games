package com.example.theestelinggames.scoreboardList;

/**
 * small class that makes a User on the scoreboard
 */
public class Scoreboard {

    public static final String SCOREBOARD_ID = "SCOREBOARD_ID";

    private String username;
    private int score;

    /**
     * basic constructor with the username and their score
     * @param username the name of the user
     * @param score the score the user has achieved
     */
    public Scoreboard(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
