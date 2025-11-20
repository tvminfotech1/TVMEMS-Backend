package com.tvm.internal.tvm_internal_project.DTO;

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

    public byte[] getpSizePhoto() { return pSizePhoto;}

    public void setpSizePhoto(byte[] pSizePhoto) { this.pSizePhoto = pSizePhoto; }
}