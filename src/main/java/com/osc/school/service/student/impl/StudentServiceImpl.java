package com.osc.school.service.student.impl;

import com.osc.school.exception.EntityIsExist;
import com.osc.school.model.entity.Student;
import com.osc.school.repository.StudentRepository;
import com.osc.school.repository.specification.StudentSpecification;
import com.osc.school.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
     private final StudentRepository studentRepository;

    @Override
    public void registerStudent(Student student) {
        Specification<Student> condition = StudentSpecification.isExisting(student.getEmail());
        Optional<Student> repositoryOne = studentRepository.findOne(condition);
        if (repositoryOne.isPresent()) {
            throw new EntityIsExist();
        }
        studentRepository.save(student);
    }
}
