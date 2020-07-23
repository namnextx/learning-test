package com.osc.school.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFilter {
    private String courseName = "";
    private int courseSize = 0;

    @NotNull
    private LocalDate dateInner = null;
}
