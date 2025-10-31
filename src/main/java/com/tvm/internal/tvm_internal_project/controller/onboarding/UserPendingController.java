package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.PendingUser;
import com.tvm.internal.tvm_internal_project.service.onboarding.PendingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userPending")
public class UserPendingController {



    @Autowired
    private PendingUserService userPendingService;

    @GetMapping("/all")
    private List<PendingUser> getAllUserPending(){
        return userPendingService.getAllUserPending();
    }



}
