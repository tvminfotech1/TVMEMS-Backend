package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nationality;
    private String ifPassport;
    private String passportNumber;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;

}

