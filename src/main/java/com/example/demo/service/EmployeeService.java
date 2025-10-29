package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        log.debug("Lấy tất cả nhân viên");
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        // Nếu client chỉ gửi department.id thì resolve thành Department entity thực
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Long deptId = employee.getDepartment().getId();
            Department dept = departmentRepository.findById(deptId)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban với ID: " + deptId));
            employee.setDepartment(dept);
        } else {
            employee.setDepartment(null);
        }
        log.info("Tạo nhân viên: name={}, email={}, dept={}",
                employee.getName(), employee.getEmail(), employee.getDepartment() != null ? employee.getDepartment().getId() : "null");
        Employee saved = employeeRepository.save(employee);
        log.info("Tạo thành công nhân viên id={}", saved.getId());
        return saved;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id));
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updated) {
        return employeeRepository.findById(id).map(exist -> {
            exist.setName(updated.getName());
            exist.setEmail(updated.getEmail());
            if (updated.getDepartment() != null && updated.getDepartment().getId() != null) {
                Department dept = departmentRepository.findById(updated.getDepartment().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban với ID: " + updated.getDepartment().getId()));
                exist.setDepartment(dept);
            } else {
                exist.setDepartment(null);
            }
            Employee saved = employeeRepository.save(exist);
            log.info("Cập nhật nhân viên id={}", saved.getId());
            return saved;
        }).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id));
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Đã xóa nhân viên id={}", id);
    }

    public List<Employee> searchEmployees(String name, String departmentName) {
        if ((name == null || name.isBlank()) && (departmentName == null || departmentName.isBlank())) {
            return employeeRepository.findAll();
        }
        if (departmentName != null && !departmentName.isBlank()) {
            return employeeRepository.findByDepartment_NameContainingIgnoreCase(departmentName);
        }
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }
}