package com.tvm.internal.tvm_internal_project.response;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserKycDto {

    private KYC kyc;
    private User user;

}