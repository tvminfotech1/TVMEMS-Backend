package com.tvm.internal.tvm_internal_project.model.onboarding;


import jakarta.persistence.*;

@Entity
public class KYCDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] panCard;
    private String panCardName;
    private String panCardType;

    @Lob
    private byte[] aadharCard;
    private String aadharCardName;
    private String aadharCardType;

    @Lob
    private byte[] passportCard;
    private String passposrtCardName;
    private String passportCardType;

    public String getPanCardName() {
        return panCardName;
    }

    public void setPanCardName(String panCardName) {
        this.panCardName = panCardName;
    }

    public String getAadharCardType() {
        return aadharCardType;
    }

    public void setAadharCardType(String aadharCardType) {
        this.aadharCardType = aadharCardType;
    }

    public String getPanCardType() {
        return panCardType;
    }

    public void setPanCardType(String panCardType) {
        this.panCardType = panCardType;
    }

    public String getAadharCardName() {
        return aadharCardName;
    }

    public void setAadharCardName(String aadharCardName) {
        this.aadharCardName = aadharCardName;
    }

    public String getPassposrtCardName() {
        return passposrtCardName;
    }

    public void setPassposrtCardName(String passposrtCardName) {
        this.passposrtCardName = passposrtCardName;
    }

    public String getPassportCardType() {
        return passportCardType;
    }

    public void setPassportCardType(String passportCardType) {
        this.passportCardType = passportCardType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPassportCard() {
        return passportCard;
    }

    public void setPassportCard(byte[] passportCard) {
        this.passportCard = passportCard;
    }

    public byte[] getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(byte[] aadharCard) {
        this.aadharCard = aadharCard;
    }

    public byte[] getPanCard() {
        return panCard;
    }

    public void setPanCard(byte[] panCard) {
        this.panCard = panCard;
    }
}
