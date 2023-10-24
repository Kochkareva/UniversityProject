package com.ulstu.University.service;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(Long id) {
        super(String.format("Lesson with id [%s] is not found", id));
    }
}
