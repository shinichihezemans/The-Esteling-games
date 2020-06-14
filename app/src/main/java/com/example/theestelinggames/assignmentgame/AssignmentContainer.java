package com.example.theestelinggames.assignmentgame;

import java.util.List;

/**
 * simple class that holds the Assignment mini-games
 */
public class AssignmentContainer {

    private String name;
    private String description;
    private List<String> assignments;

    /**
     * basic constructor
     * @param name the name of the Assignment
     * @param description short description of the Assignment
     * @param assignments a list of mini-games for the Assignment
     */
    public AssignmentContainer(String name, String description, List<String> assignments) {
        this.name = name;
        this.description = description;
        this.assignments = assignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }
}
