package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.PendingUser;

import java.util.List;


public interface PendingUserService {
    List<PendingUser> getAllUserPending();
}
