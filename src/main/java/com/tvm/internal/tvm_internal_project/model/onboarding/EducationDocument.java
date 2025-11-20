package com.tvm.internal.tvm_internal_project.model.onboarding;

import jakarta.persistence.*;
import lombok.Data;

@Data
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

}
