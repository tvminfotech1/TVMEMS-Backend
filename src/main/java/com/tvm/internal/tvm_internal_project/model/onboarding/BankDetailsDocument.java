package com.tvm.internal.tvm_internal_project.model.onboarding;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}
