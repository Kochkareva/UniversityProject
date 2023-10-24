package com.ulstu.University.controller;

import com.ulstu.University.configuration.WebConfiguration;
import com.ulstu.University.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(WebConfiguration.REST_API +"/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartment(@PathVariable Long id) {
        return new DepartmentDto(departmentService.findDepartment(id));
    }

    @GetMapping("/")
    public List<DepartmentDto> getDepartments() {
        return departmentService.findAllDepartments().stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());
    }

    /**
     * private String DepartmentName;
     * private String Login;
     * private String Password;
     */
    @PostMapping("/")
    public DepartmentDto createDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        return new DepartmentDto(departmentService.addDepartment(departmentDto.getDepartmentName(), departmentDto.getLogin(),
                departmentDto.getPassword()));
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(@PathVariable Long id,
                                          @RequestBody @Valid DepartmentDto departmentDto) {
        return new DepartmentDto(departmentService.updateDepartment(id, departmentDto.getDepartmentName(), departmentDto.getLogin(),
                departmentDto.getPassword()));
    }

    @DeleteMapping("/{id}")
    public DepartmentDto deleteDepartment(@PathVariable Long id) {
        return new DepartmentDto(departmentService.deleteDepartment(id));
    }
}
