package com.example.theestelinggames;

public class Assignment {

    private String name;
    private int attempts;
    private boolean isCompleted;

    public Assignment(String name, int attempts, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.attempts = attempts;
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

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", status=" + isCompleted +
                '}';
    }

    private static final Assignment[] staticAssignments = {
            new Assignment("test1", 1,false),
            new Assignment("test2", 0,false),
            new Assignment("test3", 2,true),
            new Assignment("test4", 3,true)
    };

    public static Assignment[] getStaticAssignments() {
        return staticAssignments;
    }

    public static Assignment getStaticAssignment(int id) {
        return staticAssignments[id];
    }
}
