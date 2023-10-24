package com.ulstu.University.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ulstu.University.model.Department;
import com.ulstu.University.repository.DepartmentRepository;
import com.ulstu.University.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ValidatorUtil validatorUtil;

    public DepartmentService(DepartmentRepository departmentRepository, ValidatorUtil validatorUtil) {
        this.departmentRepository = departmentRepository;
        this.validatorUtil = validatorUtil;
    }

    /**
     * private String DepartmentName;
     *     private String Login;
     *     private String Password;
     */
    @Transactional
    public Department addDepartment(String DepartmentName, String Login, String Password) {
        final Department department = new Department(DepartmentName, Login, Password);
        validatorUtil.validate(department);
        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public Department findDepartment(Long id) {
        final Optional<Department> department = departmentRepository.findById(id);
        return department.orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department updateDepartment(Long id, String DepartmentName, String Login, String Password) {
        final Department currentDepartment = findDepartment(id);
        currentDepartment.setDepartmentName(DepartmentName);
        currentDepartment.setLogin(Login);
        currentDepartment.setPassword(Password);
        validatorUtil.validate(currentDepartment);
        return departmentRepository.save(currentDepartment);
    }

    @Transactional
    public Department deleteDepartment(Long id) {
        final Department currentDepartment = findDepartment(id);
        departmentRepository.delete(currentDepartment);
        return currentDepartment;
    }

    @Transactional
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }
}
