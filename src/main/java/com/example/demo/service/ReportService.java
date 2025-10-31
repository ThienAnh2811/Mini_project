package com.example.demo.service;

import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final EmployeeRepository employeeRepository;

    @Cacheable(value = "employeeCountCache")
    public long getEmployeeCount() {
        System.out.println("⏳ Tính toán lại tổng số nhân viên..."); // để test cache
        return employeeRepository.count();
    }
    public Map<String, Long> getEmployeeCountByDepartment() {
        List<Object[]> results = employeeRepository.countEmployeesByDepartment();
        Map<String, Long> data = new LinkedHashMap<>();
        for (Object[] row : results) {
            data.put((String) row[0], (Long) row[1]);
        }
        return data;
    }

    public long getTotalEmployees() {
        return employeeRepository.countTotalEmployees();
    }
}