package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Final {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean checked;
    private String signature;
    private String date;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private User user;
}
