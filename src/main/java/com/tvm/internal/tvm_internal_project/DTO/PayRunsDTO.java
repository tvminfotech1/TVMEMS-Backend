package com.tvm.internal.tvm_internal_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayRunsDTO {
    private Long EmployeeId;
    private String employeeName;
    private String accountNumber;
    private String profileImageUrl;
    private String status;

}