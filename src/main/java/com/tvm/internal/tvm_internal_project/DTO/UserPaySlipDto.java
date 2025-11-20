package com.tvm.internal.tvm_internal_project.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class UserPaySlipDto {

    private Long employeeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date joiningDate;
    public UserPaySlipDto(Long employeeId, Date joiningDate) {
        this.employeeId = employeeId;
        this.joiningDate = joiningDate;
    }

}
