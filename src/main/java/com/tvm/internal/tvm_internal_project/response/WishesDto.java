package com.tvm.internal.tvm_internal_project.response;

import lombok.Data;

@Data
public class WishesDto {
    private String dob;
    private String name;
    private String category;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}