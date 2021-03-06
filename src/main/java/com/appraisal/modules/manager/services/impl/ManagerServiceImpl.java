package com.appraisal.modules.manager.services.impl;

import com.appraisal.common.EmployeeMapper;
import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.entities.EmployeeManager;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.manager.services.ManagerService;
import com.appraisal.repositories.EmployeeManagerRepository;
import com.appraisal.repositories.EmployeeRepository;
import com.appraisal.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeManagerRepository employeeManagerRepository;

    @Override
    public void addManager(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BadRequestException(ResponseCode.INVALID_EMPLOYEE));

        boolean managerExists = managerRepository.existsByEmployee(employee);

        if (managerExists) {
            throw new BadRequestException(ResponseCode.MANAGER_EXISTS);
        }

        Manager manager = Manager.builder().employee(employee).build();

        managerRepository.save(manager);
    }

    @Override
    public EmployeeModel getManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_MANAGER));

        return employeeMapper.employeeToEmployeeModel(manager.getEmployee());
    }

    @Override
    public List<EmployeeModel> getManagers(Pageable pageable) {
        Page<Manager> managers = managerRepository.findAll(pageable);
        List<Manager> managersContent = managers.getContent();

        return managersContent.stream()
                .map(this::getEmployeeModel).toList();

    }

    @Override
    public List<EmployeeModel> getEmployeesAttachedToManager(Long managerId, Pageable pageable) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_MANAGER));

        Page<EmployeeManager> allByManager = employeeManagerRepository.findAllByManager(manager, pageable);
        List<EmployeeManager> allByManagerContent = allByManager.getContent();

        return allByManagerContent.stream()
                .map(this::getEmployeeModel).toList();

    }

    private EmployeeModel getEmployeeModel(Manager manager) {
        Employee employee = manager.getEmployee();
        return buildEmployeeModel(employee);
    }

    private EmployeeModel getEmployeeModel(EmployeeManager employeeManager) {
        Employee employee = employeeManager.getEmployee();
        return buildEmployeeModel(employee);
    }

    private EmployeeModel buildEmployeeModel(Employee employee) {
        return EmployeeModel.builder()
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .dateEmployed(employee.getDateEmployed())
                .id(employee.getId())
                .email(employee.getEmail())
                .build();
    }

}
