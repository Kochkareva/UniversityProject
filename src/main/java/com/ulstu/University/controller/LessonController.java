package com.ulstu.University.controller;
import com.ulstu.University.configuration.WebConfiguration;
import com.ulstu.University.service.LessonService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(WebConfiguration.REST_API +"/lesson")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{id}")
    public LessonDto getLesson(@PathVariable Long id) {
        return new LessonDto(lessonService.findLesson(id));
    }

    @GetMapping("/")
    public List<LessonDto> getLessons() {
        return lessonService.findAllLessons().stream()
                .map(LessonDto::new)
                .collect(Collectors.toList());
    }

    /**
     * private String LessonName;
     */
    @PostMapping("/")
    public LessonDto createLesson(@RequestBody @Valid LessonDto lessonDto) {
        return new LessonDto(lessonService.addLesson(lessonDto.getLessonName(), lessonDto.getLessonDate(),
                lessonDto.getDepartment().getId()));
    }

    @PutMapping("/{id}")
    public LessonDto updateLesson(@PathVariable Long id,
                                  @RequestBody @Valid LessonDto lessonDto) {
        return new LessonDto(lessonService.updateLesson(id, lessonDto.getLessonName(), lessonDto.getLessonDate()));
    }

    @DeleteMapping("/{id}")
    public LessonDto deleteLesson(@PathVariable Long id) {
        return new LessonDto(lessonService.deleteLesson(id));
    }
}
