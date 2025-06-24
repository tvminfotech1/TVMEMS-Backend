package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.AddTimeLog;
import com.tvm.internal.tvm_internal_project.repo.AddTimeLogRepo;
import com.tvm.internal.tvm_internal_project.service.AddTimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AddTimeLogServiceimpl implements AddTimeLogService {
    @Autowired
    private AddTimeLogRepo addTimeLogRepo;

    public AddTimeLog addtime(String addTimeLogJson, MultipartFile attachment) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AddTimeLog addTimeLog = objectMapper.readValue(addTimeLogJson, AddTimeLog.class);
            if (attachment != null) {
                addTimeLog.setAttachment(attachment.getBytes());
            }
            return addTimeLogRepo.save(addTimeLog);
        } catch (IOException e) {
            throw new RuntimeException("Error processing addTimeLog creation", e);
        }
    }

    public List<AddTimeLog> getbyall() {
        return addTimeLogRepo.findAll();
    }

    public Optional<AddTimeLog> getbyid(Long id) {
        return addTimeLogRepo.findById(id);
    }

    public AddTimeLog updateTime(Long id, String addTimeLogJson, MultipartFile attachment) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AddTimeLog updatedLog = objectMapper.readValue(addTimeLogJson, AddTimeLog.class);

            AddTimeLog existingLog = addTimeLogRepo.findById(id).orElseThrow(() -> new RuntimeException("TimeLog not found with id " + id));

            existingLog.setName(updatedLog.getName());
            existingLog.setActivity(updatedLog.getActivity());
            existingLog.setDescription(updatedLog.getDescription());
            existingLog.setStartDate(updatedLog.getStartDate());
            existingLog.setEndDate(updatedLog.getEndDate());
            existingLog.setWorkMode(updatedLog.getWorkMode());
            existingLog.setWorkSystem(updatedLog.getWorkSystem());

            if (attachment != null && !attachment.isEmpty()) {
                existingLog.setAttachment(attachment.getBytes());
            }
            return addTimeLogRepo.save(existingLog);
        } catch (IOException e) {
            throw new RuntimeException("Error updating time log", e);
        }
    }


    public void deletebyid(Long id) {
        addTimeLogRepo.deleteById(id);
    }
}
