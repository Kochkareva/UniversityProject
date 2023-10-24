package com.ulstu.University;


import com.ulstu.University.model.Department;
import com.ulstu.University.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.Lesson;
import com.ulstu.University.model.TypeReporting;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaTypeReportingTest {
    private static final Logger log = LoggerFactory.getLogger(JpaDepartmentTests.class);

    @Autowired
    private TypeReportingService typeReportingService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private DepartmentService departmentService;
    @Test
    void testTypeReportingCreate() {
        typeReportingService.deleteAllTypeReportings();
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final TypeReporting typeReporting = typeReportingService.addTypeReporting(123, "зачет", department.getId());
        log.info(typeReporting.toString());
        Assertions.assertNotNull(typeReporting.getId());
    }

    @Test
    void testTypeReportingRead() {
        typeReportingService.deleteAllTypeReportings();
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
        final TypeReporting typeReporting = typeReportingService.addTypeReporting(123, "зачет", department.getId());
        log.info(typeReporting.toString());
        final TypeReporting findTypeReporting = typeReportingService.findTypeReporting(typeReporting.getId());
        log.info(typeReporting.toString());
        Assertions.assertEquals(typeReporting, findTypeReporting);
    }

    @Test
    void testTypeReportingReadNotFound() {
        typeReportingService.deleteAllTypeReportings();
        Assertions.assertThrows(TypeReportingNotFoundException.class, () -> typeReportingService.findTypeReporting(-1L));
    }

    @Test
    void testTypeReportingReadAll() {
        typeReportingService.deleteAllTypeReportings();
        disciplineService.deleteAllDisciplines();
        final Department department = departmentService.addDepartment("Кафедра", "login", "password");
         typeReportingService.addTypeReporting(123, "зачет", department.getId());
         typeReportingService.addTypeReporting(123, "зачет", department.getId());
        final List<TypeReporting> typeReportings = typeReportingService.findAllTypeReportings();
        log.info(typeReportings.toString());
        Assertions.assertEquals(typeReportings.size(), 2);
    }

    @Test
    void testTypeReportingReadAllEmpty() {
        typeReportingService.deleteAllTypeReportings();
        final List<TypeReporting> typeReportings = typeReportingService.findAllTypeReportings();
        log.info(typeReportings.toString());
        Assertions.assertEquals(typeReportings.size(), 0);
    }
/*
    @Test
    void testAddDisciplineToTypeReporting() {

        disciplineService.deleteAllDisciplines();
        typeReportingService.deleteAllTypeReportings();
        disciplineService.deleteAllDisciplines();
        final Discipline discipline = disciplineService.addDiscipline("Философия");
        final TypeReporting typeReporting = typeReportingService.addTypeReporting(123, "зачет", discipline.getId());
        typeReportingService.addDisciplineToTypeReporting(typeReporting.getId(), discipline.getId());
        log.info(typeReporting.toString());
        Assertions.assertEquals(discipline, typeReporting.getDisciplines().get(0));

    }*/
}

