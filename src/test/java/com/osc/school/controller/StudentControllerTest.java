package com.osc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.school.model.entity.Student;
import com.osc.school.model.response.ErrorResponse;
import com.osc.school.service.student.StudentService;
import com.osc.school.util.ResponseBodyMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Student> argumentCaptor;

    @Autowired
    private ResponseBodyMatchers responseBody;

    private Student studentValid;
    private Student studentInvalid;

    @BeforeEach
    void setupTest() {
        studentValid = new Student(null, "Tao Manh Duc", "taomd@nguy.com", "truong an", null);
        studentInvalid = new Student();//null, "", "");
        studentInvalid.setName("abc");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHttpRequestMatching() throws Exception {
        mockMvc.perform(post("/api/students").contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInputSerialization() throws Exception {
        mockMvc.perform(post("/api/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentValid)))
                .andExpect(status().isOk());
    }

    @Test
    void testBusinessLogicCall() throws Exception {
        mockMvc.perform(post("/api/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentValid)))
                .andExpect(status().isOk());

        verify(studentService, times(1)).registerStudent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("taomanhduc");
        assertThat(argumentCaptor.getValue().getEmail()).isEqualTo("taomd@nguy.com");
    }

    @Test
    void testValidationFailThenThrowException() throws Exception {
        ErrorResponse expectedErrorResponse = new ErrorResponse("400", "Invalid data request");
        mockMvc.perform(post("/api/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentInvalid)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody.containsObjectAsJson(expectedErrorResponse, ErrorResponse.class));
    }
}
