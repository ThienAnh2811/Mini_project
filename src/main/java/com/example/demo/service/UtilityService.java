package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {
    public String formatName(String name) {
        if (name == null || name.isEmpty()) return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
    public String generateEmployeeCode() {
        int randomNum = (int) (Math.random() * 9000) + 1000;
        return "EMP" + randomNum;
    }
}