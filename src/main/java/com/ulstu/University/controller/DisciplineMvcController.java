package com.ulstu.University.controller;

import com.ulstu.University.service.DepartmentService;
import com.ulstu.University.service.TypeReportingService;
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
import com.ulstu.University.service.DisciplineService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/discipline")
public class DisciplineMvcController {
    private final DisciplineService disciplineService;
    private final DepartmentService departmentService;
    private final TypeReportingService typeReportingService;

    public DisciplineMvcController(DisciplineService disciplineService,
                                   TypeReportingService typeReportingService,
                                   DepartmentService departmentService) {
        this.disciplineService = disciplineService;
        this.typeReportingService = typeReportingService;
        this.departmentService = departmentService;
    }
    @GetMapping
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.STUDENT, UserRole.AsString.ADMIN})
    public String getDisciplines(Model model) {
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .collect(Collectors.toList()));
        return "discipline";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String editDiscipline(@PathVariable(required = false) Long id,
                                 Model model) {
        model.addAttribute("departments",
                departmentService.findAllDepartments().stream()
                        .map(DepartmentDto::new)
                        .collect(Collectors.toList()));
        if (id == null || id <= 0) {
            model.addAttribute("disciplineDto", new DisciplineDto());
        } else {
            model.addAttribute("disciplineId", id);
            model.addAttribute("disciplineDto", new DisciplineDto(disciplineService.findDiscipline(id)));
        }
        return "discipline-edit";
    }

    /**
     * private String DisciplineName;
     */
    @PostMapping(value = {"", "/{id}"})
    public String saveDiscipline(@PathVariable(required = false) Long id,
                                 @ModelAttribute @Valid DisciplineDto disciplineDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "discipline-edit";
        }
        if (id == null || id <= 0) {
            disciplineService.addDiscipline(disciplineDto.getDisciplineName(),
                    disciplineDto.getDepartment().getId());
        } else {
            disciplineService.updateDiscipline(id,disciplineDto.getDisciplineName());
        }
        return "redirect:/discipline";
    }

    @PostMapping("/delete/{id}")
    public String deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return "redirect:/discipline";
    }

    @PostMapping("/addTypeReporting")
    public String addReportingToDiscipline( @ModelAttribute @Valid TypeReportingDisciplineDto typeReportingDisciplineDto,
                                            BindingResult bindingResult,
                                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "discipline-addTypeReporting";
        }
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        try {
            disciplineService.addTypeReportingToDiscipline(typeReportingDisciplineDto.getTypeReportingId(), typeReportingDisciplineDto.getDisciplineId());
        }catch (Exception ex){
            model.addAttribute("errors", ex.getMessage());
            return "typeReporting-addDiscipline";
        }
        return "redirect:/discipline/addTypeReporting";
    }
    @GetMapping("/addTypeReporting")
    public String getForAdding(Model model) {
        model.addAttribute("typeReportings",
                typeReportingService.findAllTypeReportings().stream()
                        .map(TypeReportingDto::new)
                        .toList());
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .toList());
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        return "discipline-addTypeReporting";
    }

    @GetMapping("/removeTypeReporting")
    public String getForRemoving(Model model) {
        model.addAttribute("typeReportings",
                typeReportingService.findAllTypeReportings().stream()
                        .map(TypeReportingDto::new)
                        .toList());
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .toList());
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        return "discipline-removeTypeReporting";
    }

    @PostMapping("/removeTypeReporting")
    public String removeDisciplineFromTypeReporting( @ModelAttribute @Valid TypeReportingDisciplineDto typeReportingDisciplineDto,
                                                     BindingResult bindingResult,
                                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "typeReporting-removeDiscipline";
        }
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        disciplineService.removeTypeReportingFromDiscipline(typeReportingDisciplineDto.getTypeReportingId(), typeReportingDisciplineDto.getDisciplineId());
        return "redirect:/typeReporting/removeDiscipline";
    }
}
