package com.osc.school.job;

import com.osc.school.model.entity.Student;
import com.osc.school.service.student.StudentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleStudent {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleStudent.class);
    private StudentService studentService;
    @Scheduled(fixedRateString = "10000")
    public void scheduleCreateStudent() {
        String studentName = UUID.randomUUID().toString();
        Student student = new Student(null, studentName, studentName + "@nguy.com", "Truong An", null);
        studentService.registerStudent(student);
    }
}
