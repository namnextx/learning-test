package com.osc.school.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.osc.school.validator.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;


import java.io.Serializable;

@Entity(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @Email(message = "email invalid")
    private String email;

    @NotNull
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
}
