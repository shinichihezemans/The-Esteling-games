package com.example.theestelinggames.scoreboardList;

import com.example.theestelinggames.mqttconnection.MQTTConnection;

public class Scoreboard {

    public static final String SCOREBOARD_ID = "SCOREBOARD_ID";

    private String score;

    public Scoreboard(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
