package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

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
    private String merital;
    private String marriegedate;

    // Current Address
    private String current_address;
    private String current_country;
    private String current_state;
    private String current_city;
    private Long current_pincode;

    // Permanent Address
    private String permanent_address;
    private String permanent_country;
    private String permanent_state;
    private String permanentCity;
    private Long permanent_pincode;

    // BCP Address
    private String bcp_address;
    private String bcp_country;
    private String bcp_state;
    private String bcp_city;
    private Long bcp_pincode;

    private Long current_contact;
    private Long permanentContact;
    private String emergency_contact_name;
    private Long emergency_contact_number;
    private String emergency_relationship;
    private String exp_year;
    private String exp_month;
    private String relevantYear;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private KYC kyc;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Passport passport;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Family family;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PreviousEmployment> previousEmployment;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Education> education;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Skills> skills;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Certification> certification;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Documents documents;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Resume resume;

    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Final aFinal;

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMerital() {
        return merital;
    }

    public void setMerital(String merital) {
        this.merital = merital;
    }

    public String getMarriegedate() {
        return marriegedate;
    }

    public void setMarriegedate(String marriegedate) {
        this.marriegedate = marriegedate;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getCurrent_country() {
        return current_country;
    }

    public void setCurrent_country(String current_country) {
        this.current_country = current_country;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public Long getCurrent_pincode() {
        return current_pincode;
    }

    public void setCurrent_pincode(Long current_pincode) {
        this.current_pincode = current_pincode;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getPermanent_country() {
        return permanent_country;
    }

    public void setPermanent_country(String permanent_country) {
        this.permanent_country = permanent_country;
    }

    public String getPermanent_state() {
        return permanent_state;
    }

    public void setPermanent_state(String permanent_state) {
        this.permanent_state = permanent_state;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public Long getPermanent_pincode() {
        return permanent_pincode;
    }

    public void setPermanent_pincode(Long permanent_pincode) {
        this.permanent_pincode = permanent_pincode;
    }

    public String getBcp_address() {
        return bcp_address;
    }

    public void setBcp_address(String bcp_address) {
        this.bcp_address = bcp_address;
    }

    public String getBcp_country() {
        return bcp_country;
    }

    public void setBcp_country(String bcp_country) {
        this.bcp_country = bcp_country;
    }

    public String getBcp_state() {
        return bcp_state;
    }

    public void setBcp_state(String bcp_state) {
        this.bcp_state = bcp_state;
    }

    public String getBcp_city() {
        return bcp_city;
    }

    public void setBcp_city(String bcp_city) {
        this.bcp_city = bcp_city;
    }

    public Long getBcp_pincode() {
        return bcp_pincode;
    }

    public void setBcp_pincode(Long bcp_pincode) {
        this.bcp_pincode = bcp_pincode;
    }

    public Long getCurrent_contact() {
        return current_contact;
    }

    public void setCurrent_contact(Long current_contact) {
        this.current_contact = current_contact;
    }

    public Long getPermanentContact() {
        return permanentContact;
    }

    public void setPermanentContact(Long permanentContact) {
        this.permanentContact = permanentContact;
    }

    public String getEmergency_contact_name() {
        return emergency_contact_name;
    }

    public void setEmergency_contact_name(String emergency_contact_name) {
        this.emergency_contact_name = emergency_contact_name;
    }

    public Long getEmergency_contact_number() {
        return emergency_contact_number;
    }

    public void setEmergency_contact_number(Long emergency_contact_number) {
        this.emergency_contact_number = emergency_contact_number;
    }

    public String getEmergency_relationship() {
        return emergency_relationship;
    }

    public void setEmergency_relationship(String emergency_relationship) {
        this.emergency_relationship = emergency_relationship;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getRelevantYear() {
        return relevantYear;
    }

    public void setRelevantYear(String relevantYear) {
        this.relevantYear = relevantYear;
    }
}