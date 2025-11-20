package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private User user;

    @Lob
    @JsonIgnore
    private byte[] panCard;

    @Lob
    @JsonIgnore
    private byte[] aadharCard;

    @Lob
    @JsonIgnore
    private byte[] pSizePhoto;

    @Lob
    @JsonIgnore
    private byte[] matric;

    @Lob
    @JsonIgnore
    private byte[] intermediate;

    @Lob
    @JsonIgnore
    private byte[] graduationMarksheet;

    @Lob
    @JsonIgnore
    private byte[] postGraduation;

    @Lob
    @JsonIgnore
    private byte[] checkLeaf;

    @Lob
    @JsonIgnore
    private byte[] passbook;

    @Transient
    @JsonProperty("panCard")
    private String panCardBase64;

    @Transient
    @JsonProperty("aadharCard")
    private String aadharCardBase64;

    @Transient
    @JsonProperty("pSizePhoto")
    private String pSizePhotoBase64;

    @Transient
    @JsonProperty("matric")
    private String matricBase64;

    @Transient
    @JsonProperty("intermediate")
    private String intermediateBase64;

    @Transient
    @JsonProperty("graduationMarksheet")
    private String graduationMarksheetBase64;

    @Transient
    @JsonProperty("postGraduation")
    private String postGraduationBase64;

    @Transient
    @JsonProperty("checkLeaf")
    private String checkLeafBase64;

    @Transient
    @JsonProperty("passbook")
    private String passbookBase64;


    public void prepareBase64Fields() {
        this.panCardBase64 = toBase64(this.panCard);
        this.aadharCardBase64 = toBase64(this.aadharCard);
        this.pSizePhotoBase64 = toBase64(this.pSizePhoto);
        this.matricBase64 = toBase64(this.matric);
        this.intermediateBase64 = toBase64(this.intermediate);
        this.graduationMarksheetBase64 = toBase64(this.graduationMarksheet);
        this.postGraduationBase64 = toBase64(this.postGraduation);
        this.checkLeafBase64 = toBase64(this.checkLeaf);
        this.passbookBase64 = toBase64(this.passbook);
    }

    private String toBase64(byte[] data) {
        return (data != null) ? java.util.Base64.getEncoder().encodeToString(data) : null;
    }

    public String getpSizePhotoBase64() {
        return pSizePhotoBase64;
    }

    public void setpSizePhotoBase64(String pSizePhotoBase64) {
        this.pSizePhotoBase64 = pSizePhotoBase64;
    }

    public byte[] getpSizePhoto() {
        return pSizePhoto;
    }

    public void setpSizePhoto(byte[] pSizePhoto) {
        this.pSizePhoto = pSizePhoto;
    }


}