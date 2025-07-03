package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.repo.PayRoleEmployeeRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.PayRoleEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PayRoleEmployeeServiceImpl implements PayRoleEmployeeService {
    @Autowired
    private PayRoleEmployeeRepo payRoleEmployeeRepo;

    public ResponseEntity<ResponseStructure<PayRoleEmployee>> SavePayRoleEmployee(PayRoleEmployee employee) {
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
}