package com.example.theestelinggames.assignmentgame;

import java.util.List;

public class AssignmentContainer {

    private String name;
    private List<String> assignments;

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
