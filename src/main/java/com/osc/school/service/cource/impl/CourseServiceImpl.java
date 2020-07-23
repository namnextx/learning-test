package com.osc.school.service.cource.impl;

import com.osc.school.exception.EntityIsExist;
import com.osc.school.exception.EntityNotFoundException;
import com.osc.school.exception.RequestValidationException;
import com.osc.school.model.entity.Course;
import com.osc.school.model.request.CourseFilter;
import com.osc.school.repository.CourseRepository;
import com.osc.school.repository.specification.CourseSpecification;
import com.osc.school.service.cource.CourseService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Course findCourseByStudentEmail(String userEmail) {
        if (Strings.isEmpty(userEmail)) {
            throw new RequestValidationException("User email is valid");
        }

        Specification<Course> courseSpecification = CourseSpecification.hasStudentEmail(userEmail);

        Optional<Course> course = courseRepository.findOne(courseSpecification);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<Course> filterCourses(@Valid CourseFilter courseFilter, Pageable pageable) {
        String courseName = courseFilter.getCourseName();
        int courseSize = courseFilter.getCourseSize();
        LocalDate dateInner = courseFilter.getDateInner();

        Specification<Course> courseSpec = CourseSpecification.filter(courseName,  courseSize, dateInner);
        return courseRepository.findAll(courseSpec, pageable);
    }

    @Override
    @Transactional
    public Course findCourseByCourseId(Long courseId) {
        return courseRepository.findCourseById(courseId);
    }

    @Override
    public void createCourse(Course course) {
        Specification<Course> hasCourse = CourseSpecification.hasCourseName(course.getCourseName());
        Optional<Course> courseResponse = courseRepository.findOne(hasCourse);
        if (courseResponse.isPresent()) {
            throw new EntityIsExist();
        }
        courseRepository.save(course);
    }
}
