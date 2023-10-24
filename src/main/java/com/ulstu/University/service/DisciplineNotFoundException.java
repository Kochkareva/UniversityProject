package com.ulstu.University.service;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(Long id) {
        super(String.format("Discipline with id [%s] is not found", id));
    }
}
