package com.example.demo.controllers;

import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportViewController {
    private final ReportService reportService;

    @GetMapping("/employees/statistics")
    public String statistics(Model model) {
        model.addAttribute("departmentStats", reportService.getEmployeeCountByDepartment());
        model.addAttribute("totalEmployees", reportService.getTotalEmployees());
        return "statistics"; // Tên file .html trong templates
    }
}