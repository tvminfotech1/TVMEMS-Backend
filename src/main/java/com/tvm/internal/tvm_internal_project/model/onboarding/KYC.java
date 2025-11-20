package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pan;
    private String panName;
    private String aadhar;
    private String aadharName;
    private String uan;
    private String pf;
    private String hdfc;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;
}
