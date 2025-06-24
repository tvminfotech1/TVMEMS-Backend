package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String skillName;
    private String skillCategories;
    private String versionNum;
    private String experience_year;
    private String experience_month;
    private String selfRate;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    @JsonBackReference
    private Personal personal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCategories() {
        return skillCategories;
    }

    public void setSkillCategories(String skillCategories) {
        this.skillCategories = skillCategories;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getExperience_year() {
        return experience_year;
    }

    public void setExperience_year(String experience_year) {
        this.experience_year = experience_year;
    }

    public String getExperience_month() {
        return experience_month;
    }

    public void setExperience_month(String experience_month) {
        this.experience_month = experience_month;
    }

    public String getSelfRate() {
        return selfRate;
    }

    public void setSelfRate(String selfRate) {
        this.selfRate = selfRate;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
