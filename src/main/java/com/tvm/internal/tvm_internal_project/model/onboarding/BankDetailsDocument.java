package com.tvm.internal.tvm_internal_project.model.onboarding;

import jakarta.persistence.*;

@Entity
public class BankDetailsDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] checkLeaf;
    private String checkLeafName;
    private String checkLeafType;

    @Lob
    private byte[] bankPassbook;
    private String bankPassBookName;
    private String bankPassbookType;

    public String getCheckLeafName() {
        return checkLeafName;
    }

    public void setCheckLeafName(String checkLeafName) {
        this.checkLeafName = checkLeafName;
    }

    public String getCheckLeafType() {
        return checkLeafType;
    }

    public void setCheckLeafType(String checkLeafType) {
        this.checkLeafType = checkLeafType;
    }

    public String getBankPassBookName() {
        return bankPassBookName;
    }

    public void setBankPassBookName(String bankPassBookName) {
        this.bankPassBookName = bankPassBookName;
    }

    public String getBankPassbookType() {
        return bankPassbookType;
    }

    public void setBankPassbookType(String bankPassbookType) {
        this.bankPassbookType = bankPassbookType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getCheckLeaf() {
        return checkLeaf;
    }

    public void setCheckLeaf(byte[] checkLeaf) {
        this.checkLeaf = checkLeaf;
    }

    public byte[] getBankPassbook() {
        return bankPassbook;
    }

    public void setBankPassbook(byte[] bankPassbook) {
        this.bankPassbook = bankPassbook;
    }
}
