package com.osc.school.service.cource;

import com.osc.school.model.entity.Course;
import com.osc.school.model.request.CourseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CourseService {
    Course findCourseByStudentEmail(String userEmail);
    Page<Course> filterCourses(CourseFilter courseFilter, Pageable pageable);
    Course findCourseByCourseId(Long courseId);
    void createCourse(Course course);
}
