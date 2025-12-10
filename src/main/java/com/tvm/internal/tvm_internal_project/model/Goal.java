package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeName;
    private String category;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate endDate;
    private String progress;
    private String status;
    private int weight;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("goal")
    @Hidden
    private User user;
}