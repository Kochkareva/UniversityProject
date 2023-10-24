package com.ulstu.University.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String DepartmentName;
    private String Login;
    private String Password;
    @OneToMany
    @JoinColumn(name="department_fk")
    private List<TypeReporting> typeReportings;
    @OneToMany
    @JoinColumn(name="department_fk")
    private List<Discipline> disciplines;
    @OneToMany
    @JoinColumn(name="department_fk")
    private List<Lesson> lessons;

    public Department() {
    }

    public Department(String DepartmentName, String Login, String Password){
        this.DepartmentName = DepartmentName;
        this.Login = Login;
        this.Password = Password;
    }

    public Long getId() {
        return id;
    }

    public String getDepartmentName(){return DepartmentName;}
    public void setDepartmentName(String DepartmentName){this.DepartmentName = DepartmentName;}

    public String getLogin(){return Login;}
    public void setLogin(String Login){this.Login=Login;}

    public String getPassword(){return Password;}
    public void setPassword(String Password){this.Password = Password;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id = " + id +
                ", DepartmentName = '" + DepartmentName + '\'' +
                ", Login = '" + Login + '\'' +
                ", Password = '" + Password + '\'' +
                '}';
    }

    public void setDisciplines (Discipline discipline)
    {
        disciplines.add(discipline);
        if(discipline.getDepartment()!= this){
            discipline.setDepartment(this);
        }
    }
    public List<Discipline> getDisciplines(){return this.disciplines;}

    public void setLessons (Lesson lesson)
    {
        lessons.add(lesson);
        if(lesson.getDepartment()!=this){
            lesson.setDepartment(this);
        }
    }
    public List<Lesson> getLessons(){return this.lessons;}

    public void setTypeReportings (TypeReporting typeReporting)
    {
        typeReportings.add(typeReporting);
        if(typeReporting.getDepartment()!= this){
            typeReporting.setDepartment(this);
        }
    }
    public List<TypeReporting> getTypeReportings(){return this.typeReportings;}
}
