package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.UserDetail;
import com.tvm.internal.tvm_internal_project.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDetailController {


    @Autowired
    private UserDetailService userDetailService;

    @PostMapping("/newuser")
    public ResponseEntity<String> createUser(@RequestBody UserDetail user) {
        return userDetailService.createUser(user);
    }

    @PostMapping("/byemail")
    public boolean checkUserByEmail(@RequestBody UserDetail userDetail) {
        return userDetailService.checkUserByEmail(userDetail.getEmail(), userDetail.getPassword());
    }

    @PostMapping("/bymob")
    public boolean checkUserByMobile(@RequestBody UserDetail userDetail) {
        return userDetailService.checkUserByMobile(userDetail.getMobile(), userDetail.getPassword());
    }
}
