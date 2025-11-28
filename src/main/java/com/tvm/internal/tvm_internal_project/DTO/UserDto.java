package com.tvm.internal.tvm_internal_project.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class UserDto {
    private Long employeeId;
    private String fullName;
    private Long mobile;
    private String email;
    private String aadhar;
    private Date dob;
    private String gender;
    private Boolean status;
    private Date joiningDate;
}
