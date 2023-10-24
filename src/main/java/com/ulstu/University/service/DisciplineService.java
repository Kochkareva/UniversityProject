package com.ulstu.University.service;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.TypeReporting;
import com.ulstu.University.repository.DepartmentRepository;
import com.ulstu.University.repository.TypeReportingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ulstu.University.repository.DisciplineRepository;
import com.ulstu.University.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    private final ValidatorUtil validatorUtil;
    private final TypeReportingRepository typeReportingRepository;
    private final DepartmentRepository departmentRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, ValidatorUtil validatorUtil,
                             TypeReportingRepository typeReportingRepository,
                             DepartmentRepository departmentRepository) {
        this.disciplineRepository = disciplineRepository;
        this.validatorUtil = validatorUtil;
        this.typeReportingRepository = typeReportingRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * private String DepartmentName;
     *     private String Login;
     *     private String Password;
     */
    @Transactional
    public Discipline addDiscipline(String DisciplineName, Long DepartmentId) {
        final Optional<Department> department = departmentRepository.findById(DepartmentId);
        final Discipline discipline = new Discipline(DisciplineName,
                department.orElseThrow(()-> new DepartmentNotFoundException(DepartmentId)));
        validatorUtil.validate(discipline);
        return disciplineRepository.save(discipline);
    }

    @Transactional(readOnly = true)
    public Discipline findDiscipline(Long id) {
        final Optional<Discipline> discipline = disciplineRepository.findById(id);
        return discipline.orElseThrow(() -> new DisciplineNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Discipline> findAllDisciplines() {
        return disciplineRepository.findAll();
    }

    @Transactional
    public Discipline updateDiscipline(Long id, String DisciplineName) {
        final Discipline currentDiscipline = findDiscipline(id);
        currentDiscipline.setDisciplineName(DisciplineName);
        validatorUtil.validate(currentDiscipline);
        return disciplineRepository.save(currentDiscipline);
    }

    @Transactional
    public Discipline deleteDiscipline(Long id) {
        final Discipline currentDiscipline = findDiscipline(id);
        disciplineRepository.delete(currentDiscipline);
        return currentDiscipline;
    }

    @Transactional
    public void deleteAllDisciplines() {
        disciplineRepository.deleteAll();
    }

    @Transactional
    public Discipline addTypeReportingToDiscipline(Long TypeReportingId, Long DisciplineId) throws Exception{
        final Optional<Discipline> discipline = disciplineRepository.findById(DisciplineId);
        final Optional<TypeReporting> typeReporting = typeReportingRepository.findById(TypeReportingId);
        /*-------------------------------------- Провека на соотвествие кафедры --------------------------------------*/
        Department departmentDiscipline = discipline.orElseThrow(()->
                new DisciplineNotFoundException(DisciplineId)).getDepartment();
        Department departmentReporting = typeReporting.orElseThrow(()->
                new TypeReportingNotFoundException(TypeReportingId)).getDepartment();
        if (departmentDiscipline.equals(departmentReporting)) {
            discipline.orElseThrow(()-> new DisciplineNotFoundException(DisciplineId)).
                    addTypeReporting(typeReporting.orElseThrow(()-> new TypeReportingNotFoundException(TypeReportingId)));
        }else {
            throw new Exception("Ошибка: Кафедры не совпадают");
        }
        /*------------------------------------------------------------------------------------------------------------*/
        return disciplineRepository.save(discipline.orElseThrow(()-> new DisciplineNotFoundException(DisciplineId)));
    }

    @Transactional
    public Discipline removeTypeReportingFromDiscipline(Long TypeReportingId, Long DisciplineId) {
        final Optional<Discipline> discipline = disciplineRepository.findById(DisciplineId);
        final Optional<TypeReporting> typeReporting = typeReportingRepository.findById(TypeReportingId);
        discipline.orElseThrow(() ->
                        new DisciplineNotFoundException(DisciplineId))
                .removeTypeReporting(typeReporting
                        .orElseThrow(() -> new TypeReportingNotFoundException(TypeReportingId)));
        return disciplineRepository.save(discipline.orElseThrow(() -> new DisciplineNotFoundException(DisciplineId)));
    }
}
