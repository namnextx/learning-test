package com.osc.school.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseFilterAndPaging {
    private CourseFilter courseFilter;
    private Pageable pageable;
}

