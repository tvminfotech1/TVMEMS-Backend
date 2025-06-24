package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId; // Required
    private String teamName; // Required
    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees; // Optional
    @OneToMany(cascade = CascadeType.ALL)
    private List<Goal> goals; // Optional

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}