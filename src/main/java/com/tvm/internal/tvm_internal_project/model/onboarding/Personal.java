package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String fname;
    private String mname;
    private String lname;
    private String email;
    private String gender;
    private String bloodGroup;
    private String dob;
    private String marital;
    private String marriegedate;
    private String current_address;
    private String current_country;
    private String current_state;
    private String current_city;
    private Long current_pincode;
    private String permanent_address;
    private String permanent_country;
    private String permanent_state;
    private String permanent_city;
    private Long permanent_pincode;
    private String bcp_address;
    private String bcp_country;
    private String bcp_state;
    private String bcp_city;
    private Long bcp_pincode;
    private Long current_contact;
    private Long permanent_contact;
    private String emergency_contact_name;
    private Long emergency_contact_number;
    private String emergency_relationship;
    private String exp_year;
    private String exp_month;
    private String relevantYear;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private User user;
}