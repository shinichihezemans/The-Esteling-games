package com.example.theestelinggames.scoreboardList;

class Scoreboard {

    private String username;
    private int score;

    Scoreboard(String username, int score) {
        this.username = username;
        this.score = score;
    }

    String getUsername() {
        return username;
    }

    int getScore() {
        return score;
    }
}
