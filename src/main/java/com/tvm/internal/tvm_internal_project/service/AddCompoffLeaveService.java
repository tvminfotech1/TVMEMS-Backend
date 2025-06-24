package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.AddCompoffLeave;

import java.util.List;
import java.util.Optional;

public interface AddCompoffLeaveService {
    AddCompoffLeave createleave(AddCompoffLeave addCompoffLeave);

    List<AddCompoffLeave> getallleave();

    Optional<AddCompoffLeave> getbyid(Long id);

    AddCompoffLeave updateleave(Long id, AddCompoffLeave addCompoffLeave);

    void deletebyid(Long id);
}
