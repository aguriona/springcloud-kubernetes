package com.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002")
public interface CourseClient {

    @DeleteMapping("/delete-course-user/{id}")
    void deleteCourseUser(@PathVariable Long id);
}
