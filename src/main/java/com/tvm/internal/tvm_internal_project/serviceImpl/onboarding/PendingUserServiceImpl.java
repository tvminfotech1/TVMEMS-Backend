package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.PendingUser;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.PendingUserRepo;

import com.tvm.internal.tvm_internal_project.service.onboarding.PendingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingUserServiceImpl implements PendingUserService {

    @Autowired
    private PendingUserRepo pendingUserRepo;

    public List<PendingUser> getAllUserPending()
    {
        return pendingUserRepo.findAll();
    }
}
