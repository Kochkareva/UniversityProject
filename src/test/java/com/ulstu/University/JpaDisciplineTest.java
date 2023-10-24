package com.ulstu.University;

import com.ulstu.University.model.Department;
import com.ulstu.University.service.DepartmentService;
import com.ulstu.University.service.DisciplineNotFoundException;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.TypeReporting;
import com.ulstu.University.service.DisciplineService;
import com.ulstu.University.service.TypeReportingService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaDisciplineTest {
    private static final Logger log = LoggerFactory.getLogger(JpaDisciplineTest.class);

    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TypeReportingService typeReportingService;

    @Test
    void testDisciplineCreate() {
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final Discipline discipline = disciplineService.addDiscipline("Math", department.getId());
        log.info(discipline.toString());
        Assertions.assertNotNull(discipline.getId());
    }

    @Test
    void testDisciplineRead() {
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final Discipline discipline = disciplineService.addDiscipline("Math", department.getId());
        log.info(discipline.toString());
        final Discipline findDiscipline = disciplineService.findDiscipline(discipline.getId());
        log.info(findDiscipline.toString());
        Assertions.assertEquals(discipline, findDiscipline);
    }

    @Test
    void testDisciplineReadNotFound() {
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final Discipline discipline = disciplineService.addDiscipline("Math", department.getId());
        disciplineService.deleteAllDisciplines();
        Assertions.assertThrows(DisciplineNotFoundException.class, () -> disciplineService.findDiscipline(-1L));
    }

    @Test
    void testDisciplineReadAll() {
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        disciplineService.addDiscipline("Философия", department.getId());
        disciplineService.addDiscipline("Философия", department.getId());
        final List<Discipline> disciplines = disciplineService.findAllDisciplines();
        log.info(disciplines.toString());
        Assertions.assertEquals(disciplines.size(), 2);
    }

    @Test
    void testDisciplineReadAllEmpty() {
        disciplineService.deleteAllDisciplines();
        final List<Discipline> disciplines = disciplineService.findAllDisciplines();
        log.info(disciplines.toString());
        Assertions.assertEquals(disciplines.size(), 0);
    }
/*
    @Test
    void testDisciplineAddTypeReporting() throws Exception {
        disciplineService.deleteAllDisciplines();
        typeReportingService.deleteAllTypeReportings();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final TypeReporting typeReporting = typeReportingService.addTypeReporting(123,"зачет", department.getId());
        final Discipline discipline = disciplineService.addDiscipline("Философия", department.getId());
        disciplineService.addTypeReportingToDiscipline(typeReporting.getId(), discipline.getId());
        Long idTypeRepodingInDiscipline = discipline.getTypeReportings().get(0).getId();
        log.info(discipline.toString());
        Assertions.assertEquals(typeReporting.getId(), idTypeRepodingInDiscipline);
    }*/
}

