package com.appraisal;

import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestData {
    public static AddEmployeeModel generateEmployeeModelRequest(){
        LocalDate now = LocalDate.now();
        return AddEmployeeModel.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dateEmployed(now)
                .build();
    }

    public static Employee generateEmployee(){
        LocalDate now = LocalDate.now();
        LocalDateTime localDateTime = now.atStartOfDay();
        Employee employee = Employee.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dateEmployed(localDateTime)
                .build();
        employee.setId(1L);
        return employee;
    }

    public static AddEmployeeModel generateEmployeeModelRequestWithManager(){
        LocalDate now = LocalDate.now();
        return AddEmployeeModel.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dateEmployed(now)
                .managerId(1L)
                .build();
    }

    public static Manager generateManager(){
        Employee employee = generateEmployee();
        Manager manager = Manager.builder()
                .employee(employee)
                .build();
        manager.setId(1L);
        return manager;
    }

}