package com.example.demo.controllers;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeViewController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees-add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department dept = departmentService.getDepartmentById(employee.getDepartment().getId());
            employee.setDepartment(dept);
        } else {
            employee.setDepartment(null); // hoặc set default dept nếu muốn
        }
        employeeService.createEmployee(employee);
        return "redirect:/employees/list";
    }
    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String department,
                                  Model model) {
        List<Employee> employees = employeeService.searchEmployees(name, department);
        model.addAttribute("employees", employees);
        return "employees-search";
    }
}