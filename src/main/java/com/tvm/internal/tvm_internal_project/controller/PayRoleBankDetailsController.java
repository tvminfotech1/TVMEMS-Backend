package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.repo.PayRoleBankDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayRoleBankDetailsController {
    @Autowired
    private PayRoleBankDetailsRepo detailsRepo;
}