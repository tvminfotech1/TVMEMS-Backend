package com.tvm.internal.tvm_internal_project.model.onboarding;

import jakarta.persistence.*;
import lombok.Data;

@Data
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

}
