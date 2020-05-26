package com.example.theestelinggames;

public class Assignment {

    private String name;
    private boolean isCompleted;
    private int score;

    public Assignment(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
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

    public void setScore(int score) {
        this.score = score;
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", status=" + isCompleted +
                '}';
    }
}
