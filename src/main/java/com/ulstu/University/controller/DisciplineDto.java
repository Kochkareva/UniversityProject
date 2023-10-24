package com.ulstu.University.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.TypeReporting;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DisciplineDto {
    private long id;
    @NotBlank(message = "DisciplineName can't be null or empty")
    private String DisciplineName;
    Department department;
    private List<TypeReporting> typeReportings;

    public DisciplineDto() {

    }

    public DisciplineDto(Discipline discipline) {
        this.id = discipline.getId();
        this.DisciplineName = discipline.getDisciplineName();
        this.department = discipline.getDepartment();
        this.typeReportings = discipline.getTypeReportings();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getDisciplineName() {
        return DisciplineName;
    }
    public void setDisciplineName(String DisciplineName) {
        this.DisciplineName = DisciplineName;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<TypeReporting> getTypeReportings() {
        return typeReportings;
    }
    public void setTypeReportings(List<TypeReporting> typeReportings) {
        this.typeReportings = typeReportings;
    }
}
