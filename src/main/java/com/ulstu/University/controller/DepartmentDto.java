package com.ulstu.University.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulstu.University.model.Department;
import com.ulstu.University.model.TypeReporting;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DepartmentDto {
    private long id;
    @NotBlank(message = "DepartmentName can't be null or empty")
    private String DepartmentName;
    @NotBlank(message = "Login can't be null or empty")
    private String Login;
    @NotBlank(message = "Password can't be null or empty")
    private String Password;

    public DepartmentDto(){

    }

    public DepartmentDto(Department department) {
        this.id = department.getId();
        this.DepartmentName = department.getDepartmentName();
        this.Login = department.getLogin();
        this.Password = department.getPassword();
    //    this.typeReportings = department.getTypeReportings();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }
    public void setDepartmentName(String DepartmentName){this.DepartmentName = DepartmentName;}

    public String getLogin(){return Login;}
    public void setLogin(String Login){this.Login=Login;}

    public String getPassword(){return Password;}
    public void setPassword(String Password){this.Password = Password;}
}
