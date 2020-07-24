package com.osc.school.service.cource;

import com.osc.school.model.entity.Course;
import com.osc.school.model.request.CourseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface CourseService {
    Course findCourseByStudentEmail(String userEmail);
    Page<Course> filterCourses(CourseFilter courseFilter, Pageable pageable);
    Course findCourseByCourseId(Long courseId);
    void createCourse(Course course);
    void addStudentsToCourse(Long courseId, Set<Long> studentIds);
}
