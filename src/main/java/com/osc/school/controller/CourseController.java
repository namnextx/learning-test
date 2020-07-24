package com.osc.school.controller;

import com.osc.school.model.entity.Course;
import com.osc.school.model.request.CourseFilter;
import com.osc.school.service.cource.CourseService;
import com.osc.school.validator.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/api/courses")
@Validated
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Course> findCourseByStudentEmail(@RequestParam("student-email") @Email String studentEmail) {
        Course courseResult = courseService.findCourseByStudentEmail(studentEmail);
        return new ResponseEntity<>(courseResult, HttpStatus.OK);
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<Page<Course>> filterCourses(@Valid @RequestBody CourseFilter courseFilter, Pageable pageable) {
        Page<Course> courseList = courseService.filterCourses(courseFilter, pageable);
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody Course course) {
        courseService.createCourse(course);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable("id") long id) {
        Course course = courseService.findCourseByCourseId(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping(value ="/{id}/students")
    public ResponseEntity<HttpStatus> addStudentsToCourse(@NotEmpty @RequestBody Set<Long> studentIds,
                                                          @PathVariable(value = "id") Long courseId) {
        courseService.addStudentsToCourse(courseId, studentIds);
        return ResponseEntity.ok().build();
    }
}
