package com.tvm.internal.tvm_internal_project.DTO;

import lombok.Data;

@Data
public class DocumentStatusDto {
    private boolean panCard;
    private boolean aadharCard;
    private boolean pSizePhoto;
    private boolean matric;
    private boolean intermediate;
    private boolean graduationMarksheet;
    private boolean postGraduation;
    private boolean checkLeaf;
    private boolean passbook;

}
