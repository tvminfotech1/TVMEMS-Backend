package com.tvm.internal.tvm_internal_project.DTO;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingResponseDTO {
    private User user;
    private Personal personal;
    private KYC kyc;
    private Passport passport;
    private Family family;
    private List<Education> education;
    private List<PreviousEmployment> previousEmployment;
    private List<Skills> skills;
    private List<Certification> certification;
    private DocumentStatusDto documents;
    private Resume resume;
    private Final aFinal;
}
