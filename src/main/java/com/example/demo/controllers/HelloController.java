package com.example.demo.controllers;

import com.example.demo.service.UtilityService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final UtilityService utilityService;
    private final ModelMapper modelMapper;

    public HelloController(UtilityService utilityService, ModelMapper modelMapper) {
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/hello")
    public String hello() {
        String empCode = utilityService.generateEmployeeCode();
        String name = utilityService.formatName("john");
        return "Hello " + name + "! Your code is: " + empCode;
    }
}