package com.tvm.internal.tvm_internal_project.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserPayroleDto {

    private Long employeeId;
    private String fullName;
    private Long mobile;
    private String email;
    private String aadhar;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date joiningDate;
    private String pan;


}