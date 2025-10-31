package com.tvm.internal.tvm_internal_project.DTO;
import com.tvm.internal.tvm_internal_project.model.Hours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimesheetDTO {
    private Long id;
    private String project;
    private Hours hours;
    private double totalhours;
    private String description;
    private String weekendDate;

    // employee fields
    private Long employeeId;
    private String employeeName;

    private String status;

}

