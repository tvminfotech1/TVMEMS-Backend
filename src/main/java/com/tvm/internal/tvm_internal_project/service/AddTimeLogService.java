package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.AddTimeLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AddTimeLogService {
    AddTimeLog addtime(String addTimeLogJson, MultipartFile attachment);

    List<AddTimeLog> getbyall();

    Optional<AddTimeLog> getbyid(Long id);

    AddTimeLog updateTime(Long id, String addTimeLogJson, MultipartFile attachment);

    void deletebyid(Long id);
}
