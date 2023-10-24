package com.ulstu.University.controller;
import com.ulstu.University.configuration.WebConfiguration;
import com.ulstu.University.service.TypeReportingService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(WebConfiguration.REST_API +"/typeReporting")
public class TypeReportingController {
    private final TypeReportingService typeReportingService;

    public TypeReportingController(TypeReportingService typeReportingService) {
        this.typeReportingService = typeReportingService;
    }

    @GetMapping("/{id}")
    public TypeReportingDto getTypeReporting(@PathVariable Long id) {
        return new TypeReportingDto(typeReportingService.findTypeReporting(id));
    }

    @GetMapping("/")
    public List<TypeReportingDto> getTypeReportings() {
        return typeReportingService.findAllTypeReportings().stream()
                .map(TypeReportingDto::new)
                .collect(Collectors.toList());
    }

    /**
     * private int ReportNumber;
     *     private String ReportName;
     *    Long DepartmentId
     */
    @PostMapping("/")
    public TypeReportingDto createTypeReporting(@RequestBody @Valid TypeReportingDto typeReportingDto) {
        return new TypeReportingDto(typeReportingService.addTypeReporting(typeReportingDto.getReportNumber(),
                typeReportingDto.getReportName(), typeReportingDto.getDepartment().getId()));
    }
    @PostMapping("/{TypeReportingId} {DisciplineId}")
    public TypeReportingDto addDiscipline(@RequestParam("TypeReportingId") Long TypeReportingId,
                                      @RequestParam("DisciplineId") Long DisciplineId) {
        try {
            return new TypeReportingDto(typeReportingService.addDisciplineToTypeReporting(TypeReportingId, DisciplineId));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    @PutMapping("/{id}")
    public TypeReportingDto updateTypeReporting(@PathVariable Long id,
                                                @RequestBody @Valid TypeReportingDto typeReportingDto) {
        return new TypeReportingDto(typeReportingService.updateTypeReporting(id, typeReportingDto.getReportNumber(),
                typeReportingDto.getReportName()));
    }

    @DeleteMapping("/{id}")
    public TypeReportingDto deleteTypeReporting(@PathVariable Long id) {
        return new TypeReportingDto(typeReportingService.deleteTypeReporting(id));
    }
}
