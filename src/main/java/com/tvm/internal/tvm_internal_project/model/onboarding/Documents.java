package com.tvm.internal.tvm_internal_project.model.onboarding;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id")
    @JsonBackReference
    private Personal personal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BankDetailsDocument_id")
    private BankDetailsDocument bankDetailsDocument;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "KYCDocument_id")
    private KYCDocument kycDocument;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EducationDocument_id")
    private EducationDocument educationDocument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public BankDetailsDocument getBankDetailsDocument() {
        return bankDetailsDocument;
    }

    public void setBankDetailsDocument(BankDetailsDocument bankDetailsDocument) {
        this.bankDetailsDocument = bankDetailsDocument;
    }

    public KYCDocument getKycDocument() {
        return kycDocument;
    }

    public void setKycDocument(KYCDocument kycDocument) {
        this.kycDocument = kycDocument;
    }

    public EducationDocument getEducationDocument() {
        return educationDocument;
    }

    public void setEducationDocument(EducationDocument educationDocument) {
        this.educationDocument = educationDocument;
    }
}
