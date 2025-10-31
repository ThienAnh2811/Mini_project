package com.example.demo.controllers;

import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/employee-count")
    public ResponseEntity<Long> getEmployeeCount() {
        long count = reportService.getEmployeeCount();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/department-count")
    public Map<String, Long> countByDepartment() {
        return reportService.getEmployeeCountByDepartment();
    }

    @GetMapping("/total-employees")
    public long totalEmployees() {
        return reportService.getTotalEmployees();
    }
}