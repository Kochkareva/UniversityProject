package com.ulstu.University.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String DisciplineName;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "disciplines_typeReportings",
            joinColumns = @JoinColumn(name = "discipline_fk"),
            inverseJoinColumns =@JoinColumn(name = "typeReporting_fk"))
    @JsonBackReference
    private List<TypeReporting> typeReportings;
    @ManyToOne
    @JoinColumn(name = "discipline_fk")
    private Department department;

    public Discipline() {
    }

    public Discipline(String DisciplineName, Department department){
        this.DisciplineName = DisciplineName;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getDisciplineName(){return DisciplineName;}
    public void setDisciplineName(String DisciplineName){this.DisciplineName = DisciplineName;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline discipline = (Discipline) o;
        return Objects.equals(id, discipline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id = " + id +
                ", DisciplineName = '" + DisciplineName + '\'' +
                '}';
    }

    public void setTypeReportings(List<TypeReporting> typeReportings) {
        this.typeReportings = typeReportings;
    }

    public List<TypeReporting> getTypeReportings() {
        return this.typeReportings;
    }

    public void addTypeReporting(TypeReporting typeReporting) {
        typeReportings.add(typeReporting);
        if (!typeReporting.getDisciplines().contains(this)) {
            typeReporting.addDiscipline(this);
        }
    }
    public void removeTypeReporting(TypeReporting typeReporting) {
        typeReportings.remove(typeReporting);
        if (typeReporting.getDisciplines().contains(this)) {
            typeReporting.removeDiscipline(this);
        }
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return department;
    }
}
