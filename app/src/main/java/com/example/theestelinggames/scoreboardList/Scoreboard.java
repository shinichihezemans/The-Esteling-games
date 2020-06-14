package com.example.theestelinggames.scoreboardList;

/**
 * small class that makes a User on the scoreboard
 */

    private String username;
    private int score;

    /**
     * basic constructor with the username and their score
     * @param username the name of the user
     * @param score the score the user has achieved
     */
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
