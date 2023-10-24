package com.ulstu.University;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ulstu.University.model.Department;
import com.ulstu.University.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaDepartmentTests {
    private static final Logger log = LoggerFactory.getLogger(JpaDepartmentTests.class);

    @Autowired
    private DepartmentService departmentService;

    @Test
    void testDepartmentCreate() {
        departmentService.deleteAllDepartments();
        final Department department = departmentService.addDepartment("Философия", "Логин", "Пароль");
        log.info(department.toString());
        Assertions.assertNotNull(department.getId());
    }

    @Test
    void testDepartmentRead() {
        departmentService.deleteAllDepartments();
        final Department department = departmentService.addDepartment("Философия", "Логин", "Пароль");
        log.info(department.toString());
        final Department findDepartment = departmentService.findDepartment(department.getId());
        log.info(findDepartment.toString());
        Assertions.assertEquals(department, findDepartment);
    }

    @Test
    void testDepartmentReadNotFound() {
        departmentService.deleteAllDepartments();
        Assertions.assertThrows(DepartmentNotFoundException.class, () -> departmentService.findDepartment(-1L));
    }

    @Test
    void testDepartmentReadAll() {
        departmentService.deleteAllDepartments();
        departmentService.addDepartment("Философия", "Логин", "Пароль");
        departmentService.addDepartment("Философия", "Логин", "Пароль");
        final List<Department> departments = departmentService.findAllDepartments();
        log.info(departments.toString());
        Assertions.assertEquals(departments.size(), 2);
    }

    @Test
    void testDepartmentReadAllEmpty() {
        departmentService.deleteAllDepartments();
        final List<Department> departments = departmentService.findAllDepartments();
        log.info(departments.toString());
        Assertions.assertEquals(departments.size(), 0);
    }
}

