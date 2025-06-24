package com.tvm.internal.tvm_internal_project.model.onboarding;

import jakarta.persistence.*;

@Entity
public class EducationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] doc_10;
    private String doc_10Name;
    private String doc_10Type;

    @Lob
    private byte[] doc_12;
    private String doc_12Name;
    private String doc_12Type;

    @Lob
    private byte[] graduationDoc;
    private String graDocName;
    private String graDocNType;

    @Lob
    private byte[] postGradutionDoc;
    private String postGraDocName;
    private String postGraDocNType;

    public String getDoc_10Name() {
        return doc_10Name;
    }

    public void setDoc_10Name(String doc_10Name) {
        this.doc_10Name = doc_10Name;
    }

    public String getDoc_10Type() {
        return doc_10Type;
    }

    public void setDoc_10Type(String doc_10Type) {
        this.doc_10Type = doc_10Type;
    }

    public String getDoc_12Name() {
        return doc_12Name;
    }

    public void setDoc_12Name(String doc_12Name) {
        this.doc_12Name = doc_12Name;
    }

    public String getDoc_12Type() {
        return doc_12Type;
    }

    public void setDoc_12Type(String doc_12Type) {
        this.doc_12Type = doc_12Type;
    }

    public String getGraDocName() {
        return graDocName;
    }

    public void setGraDocName(String graDocName) {
        this.graDocName = graDocName;
    }

    public String getGraDocNType() {
        return graDocNType;
    }

    public void setGraDocNType(String graDocNType) {
        this.graDocNType = graDocNType;
    }

    public String getPostGraDocName() {
        return postGraDocName;
    }

    public void setPostGraDocName(String postGraDocName) {
        this.postGraDocName = postGraDocName;
    }

    public String getPostGraDocNType() {
        return postGraDocNType;
    }

    public void setPostGraDocNType(String postGraDocNType) {
        this.postGraDocNType = postGraDocNType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPostGradutionDoc() {
        return postGradutionDoc;
    }

    public void setPostGradutionDoc(byte[] postGradutionDoc) {
        this.postGradutionDoc = postGradutionDoc;
    }

    public byte[] getGraduationDoc() {
        return graduationDoc;
    }

    public void setGraduationDoc(byte[] graduationDoc) {
        this.graduationDoc = graduationDoc;
    }

    public byte[] getDoc_10() {
        return doc_10;
    }

    public void setDoc_10(byte[] doc_10) {
        this.doc_10 = doc_10;
    }

    public byte[] getDoc_12() {
        return doc_12;
    }

    public void setDoc_12(byte[] doc_12) {
        this.doc_12 = doc_12;
    }
}
