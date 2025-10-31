package com.example.demo.controllers;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeViewController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments()); // thêm để filter/view
        return "employees-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees-add";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employees-add";
        }

        try {
            employeeService.createEmployee(employee);
        } catch (Exception ex) {
            bindingResult.reject("globalError", ex.getMessage());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employees-add";
        }
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String department,
                                  Model model) {
        List<Employee> employees = employeeService.searchEmployees(name, department);
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees-search";
    }
}