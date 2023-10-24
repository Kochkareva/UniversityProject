package com.ulstu.University.controller;

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
import com.ulstu.University.service.DepartmentService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/department")
public class DepartmentMvcController {
    private final DepartmentService departmentService;

    public DepartmentMvcController(DepartmentService departmentService){this.departmentService = departmentService;}
    @GetMapping
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String getDepartments(Model model) {
        model.addAttribute("departments",
                departmentService.findAllDepartments().stream()
                        .map(DepartmentDto::new)
                        .collect(Collectors.toList()));
        return "department";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.TEACHER, UserRole.AsString.ADMIN})
    public String editDepartment(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("departmentDto", new DepartmentDto());
        } else {
            model.addAttribute("departmentId", id);
            model.addAttribute("departmentDto", new DepartmentDto(departmentService.findDepartment(id)));
        }
        return "department-edit";
    }

    /**
     *
     private String DepartmentName;
     private String Login;
     private String Password;
     */
    @PostMapping(value = {"", "/{id}"})
    public String saveDepartment(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid DepartmentDto departmentDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "department-edit";
        }
        if (id == null || id <= 0) {
            departmentService.addDepartment(departmentDto.getDepartmentName(), departmentDto.getLogin(), departmentDto.getPassword());
        } else {
            departmentService.updateDepartment(id, departmentDto.getDepartmentName(), departmentDto.getLogin(), departmentDto.getPassword());
        }
        return "redirect:/department";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/department";
    }
}
