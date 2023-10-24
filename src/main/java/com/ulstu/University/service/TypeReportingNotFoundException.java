package com.ulstu.University.service;

public class TypeReportingNotFoundException extends RuntimeException {
    public TypeReportingNotFoundException(Long id) {
        super(String.format("TypeReporting with id [%s] is not found", id));
    }
}
