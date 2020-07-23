package com.osc.school.repository.specification;

import com.osc.school.model.entity.Student;
import com.osc.school.model.entity.Student_;
import org.springframework.data.jpa.domain.Specification;


public final class StudentSpecification {
    private StudentSpecification() {
    }
    public static Specification<Student> isExisting(String email) {
       return (root, query, cb) -> cb.equal(root.get(Student_.EMAIL), email);
    }
}
