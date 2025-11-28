package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.repo.onboarding.FinalRepository;
import com.tvm.internal.tvm_internal_project.service.onboarding.FinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalServiceImpl implements FinalService {

    @Autowired
    private FinalRepository finalRepository;

    public boolean isOnboardingSubmitted(Integer userId) {
        return finalRepository.existsByUser_EmployeeId(userId);
    }

}
