package com.example.theestelinggames;

public class Assignment {

//    public static final String AssignmentID = "AssignmentID";

    private String name;
    private int attempts;
    private boolean isCompleted;
    private int score;
    private int imageResourceId;


    public Assignment(String name, int attempts, boolean isCompleted, int score) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.attempts = attempts;
        this.score = score;
    }

    public Assignment(String name, int attempts, boolean isCompleted, int score, int imageResourceId) {
        this(name,attempts,isCompleted,score);
        this.imageResourceId = imageResourceId;
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

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", status=" + isCompleted +
                '}';
    }

    private static final Assignment[] staticAssignments = {
            new Assignment("test1", 1,false,0,R.drawable.astrolica),
            new Assignment("test2", 0,false,0,R.drawable.cobra),
            new Assignment("test3", 2,false,0,R.drawable.de_zwevende_belg),
            new Assignment("test4", 3,true,0,R.drawable.droomreis)
    };

    public static Assignment[] getStaticAssignments() {
        return staticAssignments;
    }

    public static Assignment getStaticAssignment(int id) {
        return staticAssignments[id];
    }
}
