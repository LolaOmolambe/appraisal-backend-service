package com.appraisal.modules.employee.services.impl;

import com.appraisal.common.EmployeeMapper;
import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.request.UpdateEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.DefaultEmployeeManagerService;
import com.appraisal.modules.employee.services.EmployeeService;
import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final DefaultEmployeeManagerService employeeManagerService;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeModel addEmployee(AddEmployeeModel employeeModel) {

        boolean userExists = userService.userExists(employeeModel.getEmail());

        if (userExists) {
            throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
        }

        employeeRepository.findEmployeeByEmail(employeeModel.getEmail())
                .ifPresent(employee -> {
                    throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
                });

        boolean employeeExists = employeeRepository.existsByEmail(employeeModel.getEmail());

        if (employeeExists) {
            throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
        }

        Employee employee = saveEmployee(employeeModel);

        if (Objects.nonNull(employeeModel.getManagerId())) {
            employeeManagerService.assignEmployeeToManager(employee.getId(), employeeModel.getManagerId());
        }

        return employeeMapper.employeeToEmployeeModel(employee);
    }

    @Override
    public EmployeeModel getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_EMPLOYEE));

        return employeeMapper.employeeToEmployeeModel(employee);
    }

    @Override
    public List<EmployeeModel> getEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        List<Employee> employeeList = employees.getContent();

        return employeeMapper.employeesToEmployeeModels(employeeList);
    }

    @Override
    public EmployeeModel updateEmployee(Long employeeId, UpdateEmployeeModel updateEmployeeModel) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BadRequestException(ResponseCode.INVALID_EMPLOYEE));

        existingEmployee.setFirstName(updateEmployeeModel.getFirstName());
        existingEmployee.setLastName(updateEmployeeModel.getLastName());
        existingEmployee.setDateEmployed(updateEmployeeModel.getDateEmployed().atStartOfDay());

        Employee employee = employeeRepository.save(existingEmployee);

        return employeeMapper.employeeToEmployeeModel(employee);
    }

    private Employee saveEmployee(AddEmployeeModel employeeModel) {
        Employee employee = employeeMapper.addEmployeeModelToEmployee(employeeModel);
        return employeeRepository.save(employee);
    }
}
