package com.osc.school;

import com.osc.school.util.ResponseBodyMatchers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport
public class SchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Bean
    ResponseBodyMatchers initResponseBodyMatchers() {
        return new ResponseBodyMatchers();
    }

}
