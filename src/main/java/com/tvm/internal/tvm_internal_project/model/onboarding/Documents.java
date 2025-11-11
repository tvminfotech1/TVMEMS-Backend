package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;

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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "personal_id")
//    @JsonBackReference
//    private Personal personal;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public byte[] getPanCard() {
        return panCard;
    }

    public void setPanCard(byte[] panCard) {
        this.panCard = panCard;
    }

    public byte[] getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(byte[] aadharCard) {
        this.aadharCard = aadharCard;
    }

    public byte[] getpSizePhoto() {
        return pSizePhoto;
    }

    public void setpSizePhoto(byte[] pSizePhoto) {
        this.pSizePhoto = pSizePhoto;
    }

    public byte[] getMatric() {
        return matric;
    }

    public void setMatric(byte[] matric) {
        this.matric = matric;
    }

    public byte[] getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(byte[] intermediate) {
        this.intermediate = intermediate;
    }

    public byte[] getGraduationMarksheet() {
        return graduationMarksheet;
    }

    public void setGraduationMarksheet(byte[] graduationMarksheet) {
        this.graduationMarksheet = graduationMarksheet;
    }

    public byte[] getPostGraduation() {
        return postGraduation;
    }

    public void setPostGraduation(byte[] postGraduation) {
        this.postGraduation = postGraduation;
    }

    public byte[] getCheckLeaf() {
        return checkLeaf;
    }

    public void setCheckLeaf(byte[] checkLeaf) {
        this.checkLeaf = checkLeaf;
    }

    public byte[] getPassbook() {
        return passbook;
    }

    public void setPassbook(byte[] passbook) {
        this.passbook = passbook;
    }

    public String getPanCardBase64() {
        return panCardBase64;
    }

    public void setPanCardBase64(String panCardBase64) {
        this.panCardBase64 = panCardBase64;
    }

    public String getAadharCardBase64() {
        return aadharCardBase64;
    }

    public void setAadharCardBase64(String aadharCardBase64) {
        this.aadharCardBase64 = aadharCardBase64;
    }

    public String getpSizePhotoBase64() {
        return pSizePhotoBase64;
    }

    public void setpSizePhotoBase64(String pSizePhotoBase64) {
        this.pSizePhotoBase64 = pSizePhotoBase64;
    }

    public String getMatricBase64() {
        return matricBase64;
    }

    public void setMatricBase64(String matricBase64) {
        this.matricBase64 = matricBase64;
    }

    public String getIntermediateBase64() {
        return intermediateBase64;
    }

    public void setIntermediateBase64(String intermediateBase64) {
        this.intermediateBase64 = intermediateBase64;
    }

    public String getGraduationMarksheetBase64() {
        return graduationMarksheetBase64;
    }

    public void setGraduationMarksheetBase64(String graduationMarksheetBase64) {
        this.graduationMarksheetBase64 = graduationMarksheetBase64;
    }

    public String getPostGraduationBase64() {
        return postGraduationBase64;
    }

    public void setPostGraduationBase64(String postGraduationBase64) {
        this.postGraduationBase64 = postGraduationBase64;
    }

    public String getCheckLeafBase64() {
        return checkLeafBase64;
    }

    public void setCheckLeafBase64(String checkLeafBase64) {
        this.checkLeafBase64 = checkLeafBase64;
    }

    public String getPassbookBase64() {
        return passbookBase64;
    }

    public void setPassbookBase64(String passbookBase64) {
        this.passbookBase64 = passbookBase64;
    }


}