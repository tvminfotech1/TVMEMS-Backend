package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public double getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(double totalhours) {
        this.totalhours = totalhours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getWeekendDate() {
        return weekendDate;
    }

    public void setWeekendDate(LocalDateTime weekendDate) {
        this.weekendDate = weekendDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
