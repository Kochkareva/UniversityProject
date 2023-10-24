package com.ulstu.University.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.Lesson;
import com.ulstu.University.model.TypeReporting;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class LessonDto {
    private long id;
    @NotBlank(message = "LessonName can't be null or empty")
    private String LessonName;
    @NotBlank(message = "LessonDate can't be null or empty")
    private String LessonDate;
    Department department;

    public LessonDto(){

    }

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.LessonName = String.format("%s",lesson.getLessonName());
        this.LessonDate = String.format("%s",lesson.getLessonDate());
        this.department = lesson.getDepartment();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getLessonName(){return  LessonName;}
    public void setLessonName(String LessonName) {this.LessonName = LessonName;}

    public String getLessonDate(){return LessonDate;}
    public void setLessonDate(String LessonDate){this.LessonDate = LessonDate;}

    public Department getDepartment(){return department;}
    public void setDepartment(Department department) {
        this.department = department;
    }
}
