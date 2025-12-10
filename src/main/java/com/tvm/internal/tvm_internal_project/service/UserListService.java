package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.DTO.UserDto;
import java.util.List;

public interface UserListService {

    List<UserDto> getAllUser();

    void deleteUser(long employeeId);
}
