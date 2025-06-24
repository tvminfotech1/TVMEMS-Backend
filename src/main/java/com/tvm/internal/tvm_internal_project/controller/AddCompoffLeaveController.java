package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.AddCompoffLeave;
import com.tvm.internal.tvm_internal_project.service.AddCompoffLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class AddCompoffLeaveController {
    @Autowired
    private AddCompoffLeaveService addCompoffLeaveService;

    @PostMapping("/compoffleave")
    public ResponseEntity<AddCompoffLeave> createleave(@RequestBody AddCompoffLeave addCompoffLeave) {
        return ResponseEntity.ok(addCompoffLeaveService.createleave(addCompoffLeave));
    }

    @GetMapping("/compoffleave")
    public ResponseEntity<List<AddCompoffLeave>> getbyall() {
        return ResponseEntity.ok(addCompoffLeaveService.getallleave());
    }

    @GetMapping("/compoffleave/{id}")
    public ResponseEntity<AddCompoffLeave> getbyid(@PathVariable Long id) {
        Optional<AddCompoffLeave> addCompoffLeave = addCompoffLeaveService.getbyid(id);
        return addCompoffLeave.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/compoffleave/{id}")
    public ResponseEntity<AddCompoffLeave> updateleave(@PathVariable Long id, @RequestBody AddCompoffLeave addCompoffLeave) {
        return ResponseEntity.ok(addCompoffLeaveService.updateleave(id, addCompoffLeave));
    }

    @DeleteMapping("/compoffleave/{id}")
    public ResponseEntity<Void> deletebyid(@PathVariable Long id) {
        addCompoffLeaveService.deletebyid(id);
        return ResponseEntity.noContent().build();
    }
}
