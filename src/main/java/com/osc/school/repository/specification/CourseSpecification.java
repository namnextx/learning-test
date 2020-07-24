package com.osc.school.repository.specification;

import com.osc.school.model.entity.Course;
import com.osc.school.model.entity.Course_;
import com.osc.school.model.entity.Student;
import com.osc.school.model.entity.Student_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Objects;

public final class CourseSpecification {
    private CourseSpecification() {
    }

    public static Specification<Course> hasCourseId(Long courseId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Course_.ID), courseId);
    }

    public static Specification<Course> hasCourseName(String courseName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Course_.COURSE_NAME), courseName);
    }

    public static Specification<Course> lessThan(int courseSize) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(Course_.COURSE_SIZE), courseSize);
    }

    public static Specification<Course> greaterThanDate(LocalDate localDate) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Course_.TO_DATE), localDate);
    }

    public static Specification<Course> lessThanDate(LocalDate localDate) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Course_.FROM_DATE), localDate);
    }

    public static Specification<Course> dateBetween(LocalDate localDate) {
        return greaterThanDate(localDate).and(lessThanDate(localDate));
    }

    public static Specification<Course> hasStudentID(Long studentId) {
        return ((Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                Join<Course, Student> courseJoin = root.join(Course_.STUDENT);
                criteriaQuery.distinct(true);
               return criteriaBuilder.equal(courseJoin.get(Student_.ID), studentId);
        });
    }

    public static Specification<Course> hasStudentEmail(String studentEmail) {
        return ((Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Join<Course, Student> courseStudentJoin = root.join(Course_.STUDENT);
            criteriaQuery.distinct(true);
            return criteriaBuilder.equal(courseStudentJoin.get(Student_.EMAIL), studentEmail);
        });
    }

    public static Specification<Course> filter(String courseName, int courseSize, LocalDate dateInter) {
        return Objects.requireNonNull(hasCourseName(courseName).or(lessThan(courseSize))).or(dateBetween(dateInter));
    }

}
