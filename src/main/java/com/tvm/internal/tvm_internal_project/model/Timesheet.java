package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String project;
    private boolean wfol;
    @Embedded
    private Hours hours;
    private double totalhours;
    private String description;
    private LocalDateTime weekendDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public boolean isWfol() {
        return wfol;
    }

    public void setWfol(boolean wfol) {
        this.wfol = wfol;
    }

    public LocalDateTime getWeekendDate() {
        return weekendDate;
    }

    public void setWeekendDate(LocalDateTime weekendDate) {
        this.weekendDate = weekendDate;
    }

    public double getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(double totalhours) {
        this.totalhours = totalhours;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


