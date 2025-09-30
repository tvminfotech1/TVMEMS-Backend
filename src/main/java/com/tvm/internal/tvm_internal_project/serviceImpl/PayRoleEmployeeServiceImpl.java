package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.PayRoleBankDetails;
import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.repo.PayRoleEmployeeRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.PayRoleEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PayRoleEmployeeServiceImpl implements PayRoleEmployeeService {
    @Autowired
    private PayRoleEmployeeRepo payRoleEmployeeRepo;


    public ResponseEntity<ResponseStructure<PayRoleEmployee>> SavePayRoleEmployee(PayRoleEmployee employee) {
        PayRoleEmployee pE=new PayRoleEmployee();
        pE.setAadhaarNumber(employee.getAadhaarNumber());
        PayRoleEmployee roleEmployee = payRoleEmployeeRepo.save(employee);
        ResponseStructure<PayRoleEmployee> roleDTO = new ResponseStructure<>();
        roleDTO.setBody(roleEmployee);
        roleDTO.setMessage("Education Details Saved Successfully!!!");
        roleDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<List<PayRoleEmployee>>> getAllEmployees() {
        List<PayRoleEmployee> payRoleEmployees = payRoleEmployeeRepo.findAll();
        ResponseStructure<List<PayRoleEmployee>> roleDto = new ResponseStructure<>();
        roleDto.setBody(payRoleEmployees);
        roleDto.setMessage("List of all Education Details");
        roleDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(roleDto, HttpStatus.OK);

    }

//    ResponseStructure<PayRoleEmployee> getEmployeeById(Long id);
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> getEmployeeById(Long id) {
        ResponseStructure<PayRoleEmployee> structure = new ResponseStructure<>();
        Optional<PayRoleEmployee> employee = payRoleEmployeeRepo.findById(id);

        if (employee.isPresent()) {
            structure.setStatusCode(HttpStatus.OK.value());
            structure.setMessage("Employee found");
            structure.setBody(employee.get());
            return new ResponseEntity<>(structure, HttpStatus.OK);
        } else {
            structure.setStatusCode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("Employee not found");
            structure.setBody(null);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> updateEmployee(Long id, PayRoleEmployee employee) {
        Optional<PayRoleEmployee> optionalEmployee = payRoleEmployeeRepo.findById(id);
        ResponseStructure<PayRoleEmployee> response = new ResponseStructure<>();

        if (optionalEmployee.isPresent()) {
            PayRoleEmployee existingEmployee = optionalEmployee.get();

            // Update fields
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setDob(employee.getDob());
            existingEmployee.setDesignation(employee.getDesignation());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setJoiningDate(employee.getJoiningDate());
            existingEmployee.setEmployeeType(employee.getEmployeeType());
            existingEmployee.setReportingManager(employee.getReportingManager());
            existingEmployee.setLocation(employee.getLocation());
            existingEmployee.setStatus(employee.getStatus());
            existingEmployee.setCtc(employee.getCtc());
            existingEmployee.setBasicSalary(employee.getBasicSalary());
            existingEmployee.setInHandSalary(employee.getInHandSalary());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setAadhaarNumber(employee.getAadhaarNumber());
            existingEmployee.setPanNumber(employee.getPanNumber());
            existingEmployee.setBloodGroup(employee.getBloodGroup());
            existingEmployee.setEmergencyContact(employee.getEmergencyContact());
            existingEmployee.setProfileImageUrl(employee.getProfileImageUrl());

            // Update bank details
            if (employee.getBankDetails() != null) {
                PayRoleBankDetails existingBank = existingEmployee.getBankDetails();
                PayRoleBankDetails newBank = employee.getBankDetails();

                if (existingBank != null) {
                    // Update existing bank details
                    existingBank.setBankName(newBank.getBankName());
                    existingBank.setAccountNumber(newBank.getAccountNumber());
                    existingBank.setIfscCode(newBank.getIfscCode());
                    existingBank.setBranch(newBank.getBranch());
                } else {
                    // Create new bank details
                    newBank.setPayRoleEmployee(existingEmployee);
                    existingEmployee.setBankDetails(newBank);
                }
            }

            // Save the updated employee
            PayRoleEmployee updatedEmployee = payRoleEmployeeRepo.save(existingEmployee);

            response.setBody(updatedEmployee);
            response.setMessage("Employee updated successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            response.setBody(null);
            response.setMessage("Employee not found with ID: " + id);
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> updateEmployeeStatus(Long id, String status) {
        Optional<PayRoleEmployee> optionalEmployee = payRoleEmployeeRepo.findById(id);
        ResponseStructure<PayRoleEmployee> response = new ResponseStructure<>();

        if (optionalEmployee.isPresent()) {
            PayRoleEmployee employee = optionalEmployee.get();
            employee.setStatus(status);
            PayRoleEmployee updatedEmployee = payRoleEmployeeRepo.save(employee);

            response.setBody(updatedEmployee);
            response.setMessage("Employee status updated successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setBody(null);
            response.setMessage("Employee not found with ID: " + id);
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}