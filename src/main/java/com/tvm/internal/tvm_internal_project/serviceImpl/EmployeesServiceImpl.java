package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Employees;
import com.tvm.internal.tvm_internal_project.repo.EmployeesRepo;
import com.tvm.internal.tvm_internal_project.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {
   
    @Autowired
    private EmployeesRepo employeeRepo;

    @Override
    public List<Employees> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employees> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Employees createEmployee(Employees employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Employees updateEmployee(Long id, Employees employeeDetails) {
        Optional<Employees> existingEmployeeOptional = employeeRepo.findById(id);

        if (existingEmployeeOptional.isPresent()) {
            Employees existingEmployee = existingEmployeeOptional.get();

            // Update fields
            existingEmployee.setEmployeeName(employeeDetails.getEmployeeName() != null ? employeeDetails.getEmployeeName() : existingEmployee.getEmployeeName());
            existingEmployee.setDetails(employeeDetails.getDetails() != null ? employeeDetails.getDetails() : existingEmployee.getDetails());
            existingEmployee.setRole(employeeDetails.getRole() != null ? employeeDetails.getRole() : existingEmployee.getRole());
            existingEmployee.setProfile(employeeDetails.getProfile() != null ? employeeDetails.getProfile() : existingEmployee.getProfile());
            existingEmployee.setMemberId(employeeDetails.getMemberId() != null ? employeeDetails.getMemberId() : existingEmployee.getMemberId());
            existingEmployee.setMembername(employeeDetails.getMembername() != null ? employeeDetails.getMembername() : existingEmployee.getMembername());
            existingEmployee.setActiveStatus(employeeDetails.isActiveStatus());
            existingEmployee.setMemberCount(employeeDetails.getMemberCount() != null ? employeeDetails.getMemberCount() : existingEmployee.getMemberCount());
            existingEmployee.setTotalCount(employeeDetails.getTotalCount() != null ? employeeDetails.getTotalCount() : existingEmployee.getTotalCount());

            // Save the updated entity
            return employeeRepo.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

}
