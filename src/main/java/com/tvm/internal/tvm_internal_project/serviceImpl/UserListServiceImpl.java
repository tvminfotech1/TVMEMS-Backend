package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.UserDto;
import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserListServiceImpl implements UserListService {

    @Autowired
    UserRepo userRepo;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setEmployeeId(user.getEmployeeId());
        dto.setFullName(user.getFullName());
        dto.setMobile(user.getMobile());
        dto.setEmail(user.getEmail());
        dto.setAadhar(user.getAadhar());
        dto.setDob(user.getDob());
        dto.setGender(user.getGender());
        dto.setStatus(user.getStatus());
        dto.setJoiningDate(user.getJoiningDate());
        return dto;
    }

    @Override
    public void deleteUser(long employeeId) {
        Optional<User> user = userRepo.findByEmployeeId(employeeId);
        if (user.isPresent()) {
            userRepo.delete(user.get());
        } else {
            throw new ResourceNotFound("User not found with employeeId: " + employeeId);
        }
    }

}
