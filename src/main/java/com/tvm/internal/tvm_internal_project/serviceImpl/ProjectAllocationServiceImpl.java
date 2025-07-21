package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.PersonalNotFoundException;
import com.tvm.internal.tvm_internal_project.model.ProjectAllocation;
import com.tvm.internal.tvm_internal_project.repo.ProjectAllocationRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.ProjectAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectAllocationServiceImpl implements ProjectAllocationService {
    @Autowired
    private ProjectAllocationRepo allocationRepo;

    public ResponseEntity<ResponseStructure<ProjectAllocation>> saveProjectAllocation(ProjectAllocation allocation) {
        ResponseStructure<ProjectAllocation> structure = new ResponseStructure<>();
        structure.setMessage("ProjectAllocation Success!!");
        structure.setBody(allocationRepo.save(allocation));
        structure.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<ProjectAllocation>> updateProjectAllocation(ProjectAllocation allocation, Integer id) {

        ResponseStructure<ProjectAllocation> structure = new ResponseStructure<>();
        Optional<ProjectAllocation> allocation1 = allocationRepo.findById(id);
        if (allocation1.isEmpty()) {
            throw new PersonalNotFoundException("Personal Id not Found :" + id);
        } else {
            ProjectAllocation projectAllocation = allocation1.get();

            projectAllocation.setName(allocation.getName());
            projectAllocation.setSkills(allocation.getSkills());
            projectAllocation.setTeam(allocation.getTeam());

            ProjectAllocation savedPersonal = allocationRepo.save(projectAllocation);
            structure.setMessage("Personal Details Updated Successfully With The Id:" + id);
            structure.setBody(savedPersonal);
            structure.setStatusCode(HttpStatus.ACCEPTED.value());

            return new ResponseEntity<>(structure, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<ResponseStructure<List<ProjectAllocation>>> findAllPersonal() {
        ResponseStructure<List<ProjectAllocation>> structure = new ResponseStructure<>();

        List<ProjectAllocation> allocations = allocationRepo.findAll();
        if (allocations.isEmpty()) {
            throw new PersonalNotFoundException("ProjectAllocation Details not Found");
        }
        structure.setMessage("List of all ProjectAllocation details");
        structure.setBody(allocations);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteById(Integer id) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        Optional<ProjectAllocation> allocation = allocationRepo.findById(id);
        if (allocation.isEmpty()) {
            throw new PersonalNotFoundException("ProjectAllocation id not found:" + id);
        }
        allocationRepo.deleteById(id);
        structure.setMessage("ProjectAllocation Deleted With The Id : " + id);
        structure.setBody("Deleted with ProjectAllocation id");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }


}
