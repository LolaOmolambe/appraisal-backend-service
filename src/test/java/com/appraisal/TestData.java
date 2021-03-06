package com.appraisal;

import com.appraisal.entities.Employee;
import com.appraisal.entities.EmployeeManager;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.request.UpdateEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class TestData {
    public static AddEmployeeModel generateEmployeeModelRequest() {
        LocalDate now = LocalDate.now();
        return AddEmployeeModel.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dateEmployed(now)
                .build();
    }

    public static Employee generateEmployee() {
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

    public static AddEmployeeModel generateEmployeeModelRequestWithManager() {
        LocalDate now = LocalDate.now();
        return AddEmployeeModel.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dateEmployed(now)
                .managerId(1L)
                .build();
    }

    public static Manager generateManager() {
        Employee employee = generateEmployee();
        Manager manager = Manager.builder()
                .employee(employee)
                .build();
        manager.setId(1L);
        return manager;
    }

    public static EmployeeModel generateEmployeeModel() {
        return EmployeeModel.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .build();
    }

    public static UpdateEmployeeModel generateUpdateEmployeeModelRequest(){
        LocalDate now = LocalDate.now();
        return UpdateEmployeeModel.builder()
                .firstName("Test")
                .lastName("Tester")
                .dateEmployed(now)
                .build();
    }

    public static Page<Employee> getEmployees() {
        Employee employee = generateEmployee();
        List<Employee> employees = List.of(employee);
        return new PageImpl(employees);
    }

    public static Page<Manager> getManagers() {
        Manager manager = generateManager();
        List<Manager> managers = List.of(manager);
        return  new PageImpl(managers);
    }

    public static Page<EmployeeManager> getEmployeeManagers() {
        Manager manager = generateManager();
        Employee employee = generateEmployee();

        EmployeeManager employeeManager = EmployeeManager.builder().manager(manager).employee(employee).build();
        employeeManager.setId(1L);

        List<EmployeeManager> employeeManagers = List.of(employeeManager);
        return new PageImpl(employeeManagers);
    }

}
