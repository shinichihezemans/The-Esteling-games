package com.example.theestelinggames;

import java.util.List;

public class AssignmentContainer {

    private String name;
    private String description;
    private List<String> assignments;

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
