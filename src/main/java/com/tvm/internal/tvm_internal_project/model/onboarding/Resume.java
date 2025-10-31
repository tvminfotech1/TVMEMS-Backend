package com.tvm.internal.tvm_internal_project.model.onboarding;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String achievements;
    private String resumeCate;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getResumeCate() {
        return resumeCate;
    }

    public void setResumeCate(String resumeCate) {
        this.resumeCate = resumeCate;
    }


}

