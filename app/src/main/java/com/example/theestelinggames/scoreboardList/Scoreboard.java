package com.example.theestelinggames.scoreboardList;

public class Scoreboard {

    public static final String SCOREBOARD_ID = "SCOREBOARD_ID";

    private String username;
    private int score;

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
