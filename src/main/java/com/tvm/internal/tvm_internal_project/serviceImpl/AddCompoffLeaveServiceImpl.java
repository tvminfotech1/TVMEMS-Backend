package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.AddCompoffLeave;
import com.tvm.internal.tvm_internal_project.repo.AddCompoffLeaveRepo;
import com.tvm.internal.tvm_internal_project.service.AddCompoffLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddCompoffLeaveServiceImpl implements AddCompoffLeaveService {
    @Autowired
    private AddCompoffLeaveRepo addCompoffLeaveRepo;

    public AddCompoffLeave createleave(AddCompoffLeave addCompoffLeave) {
        return addCompoffLeaveRepo.save(addCompoffLeave);
    }

    public List<AddCompoffLeave> getallleave() {
        return addCompoffLeaveRepo.findAll();
    }

    public Optional<AddCompoffLeave> getbyid(Long id) {
        return addCompoffLeaveRepo.findById(id);
    }

    public AddCompoffLeave updateleave(Long id, AddCompoffLeave addCompoffLeave) {
        Optional<AddCompoffLeave> addCompoffLeave1 = addCompoffLeaveRepo.findById(id);
        if (addCompoffLeave1.isPresent()) {
            AddCompoffLeave addCompoffLeave2 = addCompoffLeave1.get();
            addCompoffLeave2.setProjectName(addCompoffLeave.getProjectName());
            addCompoffLeave2.setDuration(addCompoffLeave.getDuration());
            addCompoffLeave2.setApprovedBy(addCompoffLeave.getApprovedBy());
            addCompoffLeave2.setRequestType(addCompoffLeave.getRequestType());
            addCompoffLeave2.setRequestPerson(addCompoffLeave.getRequestPerson());
            addCompoffLeave2.setStatus(addCompoffLeave.getStatus());
            return addCompoffLeaveRepo.save(addCompoffLeave2);
        } else {
            throw new RuntimeException("CompOffLeave  not found" + id);
        }
    }

    public void deletebyid(Long id) {
        addCompoffLeaveRepo.deleteById(id);
    }
}
