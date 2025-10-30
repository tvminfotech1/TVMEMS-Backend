package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserListServiceImpl implements UserListService {

    @Autowired
    UserRepo userRepo;

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(long employeeId) {
        Optional<User> user = userRepo.findByEmployeeId(employeeId);
        if (user.isPresent()) {
            userRepo.delete(user.get());
        } else {
            throw new RuntimeException("User not found with employeeId: " + employeeId);
        }
    }

}
