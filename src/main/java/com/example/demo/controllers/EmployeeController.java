package com.example.demo.controllers;

import com.example.demo.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);
    }
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employee.setId(counter.getAndIncrement());
        employees.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
}