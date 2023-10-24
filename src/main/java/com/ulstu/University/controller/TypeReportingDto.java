package com.ulstu.University.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Discipline;
import com.ulstu.University.model.Lesson;
import com.ulstu.University.model.TypeReporting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TypeReportingDto {
    private long id;
    @NotNull(message = "ReportNumber can't be null or empty")
    private int ReportNumber;
    @NotBlank(message = "ReportName can't be null or empty")
    private String ReportName;
    Department department;
    private List<Discipline> disciplines;

    public TypeReportingDto(){

    }
    public TypeReportingDto(TypeReporting typeReporting) {
        this.id = typeReporting.getId();
        this.ReportNumber = typeReporting.getReportNumber();
        this.ReportName = String.format("%s",typeReporting.getReportName());
        this.department = typeReporting.getDepartment();
        this.disciplines=typeReporting.getDisciplines();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public int getReportNumber(){return ReportNumber;}
    public void setReportNumber(int ReportNumber){this.ReportNumber = ReportNumber;}

    public String getReportName(){return ReportName;}
    public void setReportName(String ReportName){this.ReportName = ReportName;}

    public Department getDepartment(){return department;}
    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Discipline> getDisciplines() {return disciplines;}
    public void setDisciplines(List<Discipline> disciplines) {this.disciplines = disciplines;}
}
