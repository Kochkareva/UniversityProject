package com.ulstu.University.controller;

import com.ulstu.University.service.DepartmentService;
import com.ulstu.University.user.model.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ulstu.University.service.LessonService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lesson")
public class LessonMvcController {
    private final LessonService lessonService;
    private final DepartmentService departmentService;

    public LessonMvcController(LessonService lessonService, DepartmentService departmentService) {
        this.lessonService = lessonService;
        this.departmentService = departmentService;
    }
    @GetMapping
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.STUDENT, UserRole.AsString.ADMIN})
    public String getLessons(Model model) {
        model.addAttribute("lessons",
                lessonService.findAllLessons().stream()
                        .map(LessonDto::new)
                        .collect(Collectors.toList()));
        return "lesson";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String editLesson(@PathVariable(required = false) Long id,
                                 Model model) {
        model.addAttribute("departments",
                departmentService.findAllDepartments().stream()
                        .map(DepartmentDto::new)
                        .collect(Collectors.toList()));
        if (id == null || id <= 0) {
            model.addAttribute("lessonDto", new LessonDto());
        } else {
            model.addAttribute("lessonId", id);
            model.addAttribute("lessonDto", new LessonDto(lessonService.findLesson(id)));
        }
        return "lesson-edit";
    }

    /**
     *
     private String LessonName;
     private String LessonDate;
     */
    @PostMapping(value = {"", "/{id}"})
    public String saveLesson(@PathVariable(required = false) Long id,
                                 @ModelAttribute @Valid LessonDto lessonDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "lesson-edit";
        }
        if (id == null || id <= 0) {
            lessonService.addLesson(lessonDto.getLessonName(), lessonDto.getLessonDate(), lessonDto.getDepartment().getId());
        } else {
            lessonService.updateLesson(id,lessonDto.getLessonName(), lessonDto.getLessonDate());
        }
        return "redirect:/lesson";
    }

    @PostMapping("/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return "redirect:/lesson";
    }
}
