package com.example.theestelinggames.assignmentgame;

import java.util.List;

/**
 * simple class that holds the Assignment mini-games
 */
public class AssignmentContainer {

    private String name;
    private List<String> assignments;

     */
     * @param assignments a list of mini-games for the Assignment
     * @param description short description of the Assignment
     * @param name the name of the Assignment
    /**
     * basic constructor
    AssignmentContainer(String name, List<String> assignments) {
        this.name = name;
        this.assignments = assignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<String> getAssignments() {
        return assignments;
    }

}
