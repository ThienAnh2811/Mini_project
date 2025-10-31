package com.example.demo.controllers;

import com.example.demo.entity.Department;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> list = departmentService.getAllDepartments();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
        Department saved = departmentService.createDepartment(department);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        log.info("Tạo phòng ban id={} name={}", saved.getId(), saved.getName());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            Department dept = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(dept);
        } catch (ResourceNotFoundException ex) {
            log.warn("Không tìm thấy department id={}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody Department departmentReq
    ) {
        try {
            Department updated = departmentService.updateDepartment(id, departmentReq);
            log.info("Cập nhật phòng ban id={}", id);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException ex) {
            log.warn("Không tìm thấy department để cập nhật id={}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            log.info("Đã xóa department id={}", id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            log.warn("Không tìm thấy department để xóa id={}", id);
            return ResponseEntity.notFound().build();
        }
    }
}