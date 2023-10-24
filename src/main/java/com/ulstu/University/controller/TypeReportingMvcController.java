package com.ulstu.University.controller;

import com.ulstu.University.service.DepartmentService;
import com.ulstu.University.service.DisciplineService;
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
import com.ulstu.University.service.TypeReportingService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/typeReporting")
public class TypeReportingMvcController {
    private final TypeReportingService typeReportingService;
    private final DepartmentService departmentService;
    private final DisciplineService disciplineService;

    public TypeReportingMvcController(TypeReportingService typeReportingService, DepartmentService departmentService,
                                      DisciplineService disciplineService){
        this.typeReportingService = typeReportingService;
        this.departmentService = departmentService;
        this.disciplineService = disciplineService;
    }
    @GetMapping()
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String getTypeReportings(Model model) {
        model.addAttribute("typeReportings",
                typeReportingService.findAllTypeReportings().stream()
                        .map(TypeReportingDto::new)
                        .collect(Collectors.toList()));
        return "typeReporting";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String editTypeReporting(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("departments",
                departmentService.findAllDepartments().stream()
                        .map(DepartmentDto::new)
                        .collect(Collectors.toList()));
        if (id == null || id <= 0) {
            model.addAttribute("typeReportingDto", new TypeReportingDto());
        } else {
            model.addAttribute("typeReportingId", id);
            model.addAttribute("typeReportingDto", new TypeReportingDto(typeReportingService.findTypeReporting(id)));
        }
        return "typeReporting-edit";
    }

    /**
     *
     private int ReportNumber;
     private String ReportName;
     */
    @PostMapping(value = {"", "/{id}"})
    public String saveTypeReporting(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid TypeReportingDto typeReportingDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "typeReporting-edit";
        }
        if (id == null || id <= 0) {
            typeReportingService.addTypeReporting(typeReportingDto.getReportNumber(),
                    typeReportingDto.getReportName(), typeReportingDto.getDepartment().getId());
        } else {
            typeReportingService.updateTypeReporting(id, typeReportingDto.getReportNumber(),
                    typeReportingDto.getReportName());
        }
        return "redirect:/typeReporting";
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeReporting(@PathVariable Long id) {
        typeReportingService.deleteTypeReporting(id);
        return "redirect:/typeReporting";
    }

    @PostMapping("/addDiscipline")
    public String addDisciplineToReporting( @ModelAttribute @Valid TypeReportingDisciplineDto typeReportingDisciplineDto,
                                       BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "typeReporting-addDiscipline";
        }
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        try {
            typeReportingService.addDisciplineToTypeReporting(typeReportingDisciplineDto.getTypeReportingId(), typeReportingDisciplineDto.getDisciplineId());
        }
        catch (Exception ex){
            model.addAttribute("errors", ex.getMessage());
            return "typeReporting-addDiscipline";
        }
        return "redirect:/typeReporting/addDiscipline";
    }
    @GetMapping("/addDiscipline")
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
        return "typeReporting-addDiscipline";
    }

    @GetMapping("/removeDiscipline")
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
        return "typeReporting-removeDiscipline";
    }

    @PostMapping("/removeDiscipline")
    public String removeDisciplineFromTypeReporting( @ModelAttribute @Valid TypeReportingDisciplineDto typeReportingDisciplineDto,
                                            BindingResult bindingResult,
                                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "typeReporting-removeDiscipline";
        }
        model.addAttribute("typeReportingDisciplineDto", new TypeReportingDisciplineDto());
        typeReportingService.removeDisciplineFromTypeReporting(typeReportingDisciplineDto.getTypeReportingId(), typeReportingDisciplineDto.getDisciplineId());
        return "redirect:/typeReporting/removeDiscipline";
    }
}
