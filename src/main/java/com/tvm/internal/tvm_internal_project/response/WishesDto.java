package com.tvm.internal.tvm_internal_project.response;

import lombok.Data;

import java.util.Date;

@Data
public class WishesDto {
    private String name;
    private  byte[] pSizePhoto;
    private Date  joiningDate;
    private Date dob;
    private String email;
    private String totalExp;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public byte[] getpSizePhoto() {
        return pSizePhoto;
    }

    public void setpSizePhoto(byte[] pSizePhoto) {
        this.pSizePhoto = pSizePhoto;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(String totalExp) {
        this.totalExp = totalExp;
    }
}