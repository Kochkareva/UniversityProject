package com.ulstu.University.service;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.Lesson;
import com.ulstu.University.model.TypeReporting;
import com.ulstu.University.repository.DepartmentRepository;
import com.ulstu.University.repository.DisciplineRepository;
import com.ulstu.University.repository.TypeReportingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ulstu.University.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class TypeReportingService {
    private final TypeReportingRepository typeReportingRepository;
    private final DisciplineRepository disciplineRepository;
    private final DepartmentRepository departmentRepository;
    private final ValidatorUtil validatorUtil;

    public TypeReportingService(TypeReportingRepository typeReportingRepository,
                                DepartmentRepository departmentRepository,
                                DisciplineRepository disciplineRepository,
                                ValidatorUtil validatorUtil) {
        this.typeReportingRepository = typeReportingRepository;
        this.disciplineRepository = disciplineRepository;
        this.departmentRepository = departmentRepository;
        this.validatorUtil = validatorUtil;
    }

    /**
     *
     private int ReportNumber;
     private String ReportName;
     */
    @Transactional
    public TypeReporting addTypeReporting(int ReportNumber, String ReportName, Long DepartmentId) {
        final Optional<Department> department = departmentRepository.findById(DepartmentId);
        final TypeReporting typeReporting = new TypeReporting(ReportNumber, ReportName,
                department.orElseThrow(()-> new DepartmentNotFoundException(DepartmentId)));
        validatorUtil.validate(typeReporting);
        return typeReportingRepository.save(typeReporting);
    }

    @Transactional(readOnly = true)
    public TypeReporting findTypeReporting(Long id) {
        final Optional<TypeReporting> typeReporting = typeReportingRepository.findById(id);
        return typeReporting.orElseThrow(() -> new TypeReportingNotFoundException(id));
    }
    @Transactional
    public TypeReporting addDisciplineToTypeReporting(Long TypeReportingId, Long DisciplineId) throws Exception {
        final Optional<Discipline> discipline = disciplineRepository.findById(DisciplineId);
        final Optional<TypeReporting> typeReporting = typeReportingRepository.findById(TypeReportingId);
        /*-------------------------------------- Провека на соотвествие кафедры --------------------------------------*/
        Department departmentDiscipline = discipline.orElseThrow(()->
                new DisciplineNotFoundException(DisciplineId)).getDepartment();
        Department departmentReporting = typeReporting.orElseThrow(()->
                new TypeReportingNotFoundException(TypeReportingId)).getDepartment();
        if (departmentDiscipline.equals(departmentReporting)) {
            typeReporting.orElseThrow(() -> new TypeReportingNotFoundException(TypeReportingId)).
                    addDiscipline(discipline.orElseThrow(() -> new DisciplineNotFoundException(DisciplineId)));
        }else {
            throw new Exception("Ошибка: Кафедры не совпадают");
        }
        /*------------------------------------------------------------------------------------------------------------*/
        return typeReportingRepository.save(typeReporting.orElseThrow(()->new TypeReportingNotFoundException(TypeReportingId)));
    }

    @Transactional
    public TypeReporting removeDisciplineFromTypeReporting(Long TypeReportingId, Long DisciplineId) {
        final Optional<Discipline> discipline = disciplineRepository.findById(DisciplineId);
        final Optional<TypeReporting> typeReporting = typeReportingRepository.findById(TypeReportingId);
        typeReporting.orElseThrow(() ->
                        new TypeReportingNotFoundException(TypeReportingId))
                .removeDiscipline(discipline
                        .orElseThrow(() -> new DisciplineNotFoundException(DisciplineId)));
        return typeReportingRepository.save(typeReporting.orElseThrow(() -> new TypeReportingNotFoundException(TypeReportingId)));
    }

    @Transactional(readOnly = true)
    public List<TypeReporting> findAllTypeReportings() {
        return typeReportingRepository.findAll();
    }

    @Transactional
    public TypeReporting updateTypeReporting(Long id, int ReportNumber, String ReportName) {
        final TypeReporting currentTypeReporting = findTypeReporting(id);
        currentTypeReporting.setReportNumber(ReportNumber);
        currentTypeReporting.setReportName(ReportName);
        validatorUtil.validate(currentTypeReporting);
        return typeReportingRepository.save(currentTypeReporting);
    }

    @Transactional
    public TypeReporting deleteTypeReporting(Long id) {
        final TypeReporting currentTypeReporting = findTypeReporting(id);
        typeReportingRepository.delete(currentTypeReporting);
        return currentTypeReporting;
    }

    @Transactional
    public void deleteAllTypeReportings() {
        typeReportingRepository.deleteAll();
    }
}
