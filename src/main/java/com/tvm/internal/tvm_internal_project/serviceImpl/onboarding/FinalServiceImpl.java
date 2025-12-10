package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.FinalRepository;
import com.tvm.internal.tvm_internal_project.service.onboarding.FinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalServiceImpl implements FinalService {

    @Autowired
    private FinalRepository finalRepository;

    @Autowired
    private UserRepo userRepo;

    public boolean isOnboardingSubmitted(Integer userId) {
        boolean userExists = userRepo.existsByEmployeeId(userId);
        if (!userExists) {
            throw new ResourceNotFound("Wrong Employee ID: " + userId);
        }
        return finalRepository.existsByUser_EmployeeId(userId);
    }

}
