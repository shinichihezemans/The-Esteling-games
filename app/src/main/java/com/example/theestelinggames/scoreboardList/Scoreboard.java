package com.example.theestelinggames.scoreboardList;

/**
 * small class that makes a User on the scoreboard
 */
class Scoreboard {
    private String username;
    private int score;

    /**
     * Basic constructor of Scoreboard.
     *
     * @param username The name of the user
     * @param score    The score the user has achieved
     */
    Scoreboard(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Getter for the username.
     *
     * @return The variable username.
     */
    String getUsername() {
        return username;
    }

    /**
     * Getter for the score.
     *
     * @return The variable score.
     */
    int getScore() {
        return score;
    }
}
