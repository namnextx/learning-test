package com.osc.school.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.school.model.entity.Course;
import com.osc.school.model.entity.Student;
import com.osc.school.model.entity.Teacher;
import com.osc.school.model.request.CourseFilter;
import com.osc.school.model.response.ErrorResponse;
import com.osc.school.service.cource.CourseService;
import com.osc.school.util.ResponseBodyMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.linesOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.BDDAssertions.then;

@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Captor
    ArgumentCaptor<Object> argumentCaptor;

    @Captor
    private ArgumentCaptor<CourseFilter> filterArgumentCaptor;

    @Autowired
    private ResponseBodyMatchers responseBody;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CourseFilter courseFilter;

    private Course courseRegister;

    @Mock
    private CourseFilter courseFilterInvalid;

    @BeforeEach
    void setUpTest() {
        courseFilter = new CourseFilter("xic bic", 20, LocalDate.of(2020, 7, 23));
        courseFilterInvalid = new CourseFilter("xic bic", 20, null);
        courseRegister = new Course();
        courseRegister.setCourseName("Chibi");
        courseRegister.setCourseSize(30);
        courseRegister.setFromDate(LocalDate.of(2020, 3, 2));
        courseRegister.setToDate(LocalDate.of(2020, 10, 2));
        courseRegister.setStudent(Collections.emptyList());
        courseRegister.setTeacher(new Teacher());
    }

    private static final String URI_COURSE_FILTER = "/api/courses/filter";
    private static final String URI_COURSE = "/api/courses";

    @Test
    void testCoursesRequestMatching() throws Exception {
        mockMvc.perform(get("/api/courses")
                .param("student-email", "taomd@nguy.com"))
                .andExpect(status().isOk());
    }

    @Test
    void testCoursesRequestInvalidStudentEmail_ThenReturn400() throws Exception {
        mockMvc.perform(get("/api/courses")
                .param("student-email", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCourseRequestValidThenMapsToParam() throws Exception {
        String emailParams = "taothao@nguy.com";
        mockMvc.perform(get("/api/courses")
                .param("student-email", emailParams));
        verify(courseService, times(1)).findCourseByStudentEmail((String) argumentCaptor.capture());
        then(argumentCaptor.getValue()).isEqualTo("taothao@nguy.com");
    }

    @Test
    void testCourseRequestValidAndReturnThenVerifyResponseOK() throws Exception {
        Course courseRequest = new Course();
        Student student = new Student();
        student.setEmail("taomd@nguy.com");
        List<Student> students = new ArrayList<>();
        students.add(student);
        courseRequest.setStudent(students);
        String emailParams = "taomd@nguy.com";
        Mockito.when(courseService.findCourseByStudentEmail(Mockito.anyString())).thenReturn(courseRequest);
        MvcResult mvcResult = mockMvc.perform(get("/api/courses")
                .param("student-email", emailParams)
                .contentType("application/json"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        then(contentAsString).isEqualTo(objectMapper.writeValueAsString(courseRequest));
    }

    @Test
    void testCourseRequestInvalidEmailThenReturns400AndErrorMessage() throws Exception {
        ErrorResponse expectedErrorResponse = new ErrorResponse("400", "Invalid data input");
        mockMvc.perform(get("/api/courses")
                .param("student-email", "taom")
                .contentType("application/json"))
                .andExpect(responseBody.containsObjectAsJson(expectedErrorResponse, ErrorResponse.class));
    }


    @Test
    void testCourseFilterSerializationOK() throws Exception {
        mockMvc.perform(post(URI_COURSE_FILTER)
                .param("page", "1")
                .param("size", "10")
                .param("sort", "id,desc")
                .content(objectMapper.writeValueAsString(courseFilter))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testCourseFilterBusinessOK() throws Exception {
        mockMvc.perform(post(URI_COURSE_FILTER)
                .content(objectMapper.writeValueAsString(courseFilter))
                .contentType("application/json")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "id,desc"))
                .andExpect(status().isOk());
        verify(courseService, times(1))
                .filterCourses(filterArgumentCaptor.capture(), (Pageable) argumentCaptor.capture());

        PageRequest pageable = (PageRequest) argumentCaptor.getValue();

        Sort sort = Sort.by("id").descending();
        then(pageable.getPageNumber()).isEqualTo(1);
        then(pageable.getPageSize()).isEqualTo(10);
        then(pageable.getSort()).isEqualTo(sort);

        then((filterArgumentCaptor.getValue()).getCourseName()).isEqualTo("xic bic");
        then((filterArgumentCaptor.getValue()).getCourseSize()).isEqualTo(20);
        then((filterArgumentCaptor.getValue()).getDateInner()).isEqualTo("2020-07-23");
    }

    @Test
    void testCourseFilterHandleExceptionFieldInvalid() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse("400", "Invalid data request");
        mockMvc.perform(post(URI_COURSE_FILTER)
                .content(objectMapper.writeValueAsString(courseFilterInvalid))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody.containsObjectAsJson(errorResponse, ErrorResponse.class));
    }

    @Test
    void testRegisterCourseSerialization() throws Exception {
        mockMvc.perform(post(URI_COURSE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseRegister)))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterCourseBusinessOK() throws Exception {
/*        mockMvc.perform(post(URI_COURSE)
                    .content(objectMapper.writeValueAsString(courseRegister))
                    .contentType("application/json"))
                .andExpect(status().isOk());
        verify(courseService, times(1)).createCourse((Course)argumentCaptor.capture());
        Course courseRegistered = (Course) argumentCaptor.getValue();
        then(courseRegistered.getCourseName()).isEqualTo("Chibi");
        then(courseRegistered.getCourseSize()).isEqualTo(30);
        then(courseRegistered.getFromDate()).isEqualTo(LocalDate.of(2020, 3, 2));
        then(courseRegistered.getToDate()).isEqualTo(LocalDate.of(2020, 10, 2));*/

        Map<Integer, Integer> map = new HashMap<>();
        int[] as = new int[]{1, 2, 5, 62, 1, 2, 4};
        Set<Integer> as1 = Arrays.stream(as).parallel().boxed().collect(Collectors.toSet());

        int[] bs = new int[]{1, 2, 5, 5, 7, 9, 62, 1, 2, 4};
        Set<Integer> ls = new HashSet<>();
        for (int b : bs) {
            if (!as1.contains(b)) {
                ls.add(b);
            }
        }
       /* for (int a : as) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        for (int b : bs) {
            if (map.get(b) == null) {
                ls.add(b);
            } else {
                map.put(b, map.get(b) - 1);
                 if (map.get(b) == 0) {
                     map.remove(b);
                 }
            }
        }
        map.forEach((k, v) -> {
            for (Integer i = 0; i < v; i++) {
                ls.add(k);
            }
        });*/
        System.out.println(Arrays.toString(ls.toArray()));
    }


}
