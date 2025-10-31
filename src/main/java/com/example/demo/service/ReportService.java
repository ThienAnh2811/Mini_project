package com.example.demo.service;

import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final EmployeeRepository employeeRepository;

    @Cacheable(value = "employeeCountCache")
    public long getEmployeeCount() {
        System.out.println("⏳ Tính toán lại tổng số nhân viên..."); // để test cache
        return employeeRepository.count();
    }
}