package com.ulstu.University.controller;

public class TypeReportingDisciplineDto {
    private Long disciplineId;
    private Long typeReportingId;

    public TypeReportingDisciplineDto(){

    }
    public Long getDisciplineId(){return disciplineId;}
    public void setDisciplineId(Long disciplineId){this.disciplineId = disciplineId;}

    public Long getTypeReportingId(){return typeReportingId;}
    public void setTypeReportingId(Long typeReportingId){this.typeReportingId = typeReportingId;}
}
