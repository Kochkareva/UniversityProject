package com.ulstu.University.service;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Lesson;
import com.ulstu.University.model.TypeReporting;
import com.ulstu.University.repository.DepartmentRepository;
import com.ulstu.University.repository.LessonRepository;
import com.ulstu.University.repository.TypeReportingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ulstu.University.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final TypeReportingRepository typeReportingRepository;
    private final DepartmentRepository departmentRepository;
    private final ValidatorUtil validatorUtil;

    public LessonService(LessonRepository lessonRepository, TypeReportingRepository typeReportingRepository,
                         ValidatorUtil validatorUtil, DepartmentRepository departmentRepository) {
        this.lessonRepository = lessonRepository;
        this.typeReportingRepository = typeReportingRepository;
        this.validatorUtil = validatorUtil;
        this.departmentRepository = departmentRepository;
    }

    /**
     * private String LessonName;
     *     private String LessonDate;
     */
    @Transactional
    public Lesson addLesson(String LessonName, String LessonDate, Long DepartmentId) {
        final Optional<Department> department = departmentRepository.findById(DepartmentId);
        final Lesson lesson = new Lesson(LessonName, LessonDate,
                department.orElseThrow(()-> new DepartmentNotFoundException(DepartmentId)));
        validatorUtil.validate(lesson);
        return lessonRepository.save(lesson);
    }

    @Transactional(readOnly = true)
    public Lesson findLesson(Long id) {
        final Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.orElseThrow(() -> new LessonNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    @Transactional
    public Lesson updateLesson(Long id, String LessonName, String LessonDate) {
        final Lesson currentLesson = findLesson(id);
        currentLesson.setLessonName(LessonName);
        currentLesson.setLessonDate(LessonDate);
        validatorUtil.validate(currentLesson);
        return lessonRepository.save(currentLesson);
    }

    @Transactional
    public Lesson deleteLesson(Long id) {
        final Lesson currentLesson = findLesson(id);
        lessonRepository.delete(currentLesson);
        return currentLesson;
    }

    @Transactional
    public void deleteAllLessons() {
        lessonRepository.deleteAll();
    }
}
