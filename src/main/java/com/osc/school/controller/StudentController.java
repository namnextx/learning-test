package com.osc.school.controller;

import com.osc.school.model.entity.Student;
import com.osc.school.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}



