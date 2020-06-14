package com.example.theestelinggames.assignmentgame;

import java.util.List;

/**
 * simple class that holds the Assignment mini-games
 */
public class AssignmentContainer {

    private String name;
    private List<String> assignments;

    /**
     * Basic constructor of AssignmentContainer.
     *
     * @param assignments A list of mini-games for the Assignment.
     * @param name        The name of the Assignment.
     */
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
