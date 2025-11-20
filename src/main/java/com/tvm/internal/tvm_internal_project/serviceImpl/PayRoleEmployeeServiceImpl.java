package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.PayRunsDTO;
import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.PayRoleEmployeeRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.DTO.UserPaySlipDto;
import com.tvm.internal.tvm_internal_project.service.PayRoleEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PayRoleEmployeeServiceImpl implements PayRoleEmployeeService {
    @Autowired
    private PayRoleEmployeeRepo payRoleEmployeeRepo;

    @Autowired
    private UserRepo userRepo;

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
            existingEmployee.setFullName(employee.getFullName());
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

    @Override
    public List<PayRunsDTO> getPayRunsData(String month) {
        List<PayRunsDTO> details = payRoleEmployeeRepo.findAll()
                .stream()
                .filter(emp -> emp.getSalaryHistoryList() != null &&
                        emp.getSalaryHistoryList()
                                .stream()
                                .anyMatch(salary -> salary.getMonth().equals(month)))
                .map(emp -> new PayRunsDTO(
                        emp.getId(),
                        emp.getFullName() ,
                        emp.getBankDetails() != null ? emp.getBankDetails().getAccountNumber() : null,
                        emp.getProfileImageUrl() != null ? emp.getProfileImageUrl() : null,
                        emp.getStatus()
                ))
                .collect(Collectors.toList());
        return details;
    }

    @Override
    public void deletePayrole(Long id) {
        payRoleEmployeeRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<ResponseStructure<UserPaySlipDto>> getPayRunsUser(Long id) {
        ResponseStructure<UserPaySlipDto> response = new ResponseStructure<>();
        try {
            User employee = userRepo.findById(id)
                    .orElseThrow();
            UserPaySlipDto dto = new UserPaySlipDto(
                    employee.getEmployeeId(),
                    employee.getJoiningDate()
            );
            response.setBody(dto);
            response.setMessage("Employee joining date fetched successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setBody(null);
            response.setMessage("Error: " + e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}