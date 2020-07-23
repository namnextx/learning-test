package com.osc.school.repository;


import com.osc.school.model.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    public StudentRepositoryTest() {
    }

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setName("nguydien");
        student.setEmail("nguydien@phanchu.com");
    }

    @Test
    void testStudentSaveSuccess() {
        Student studentSaveSuccess = studentRepository.save(student);
        then(student.getEmail()).isEqualTo(studentSaveSuccess.getEmail());
        then(student.getName()).isEqualTo(studentSaveSuccess.getName());
    }
}
