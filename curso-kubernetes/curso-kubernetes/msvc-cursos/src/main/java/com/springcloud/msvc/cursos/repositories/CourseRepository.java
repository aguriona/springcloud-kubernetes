package com.springcloud.msvc.cursos.repositories;

import com.springcloud.msvc.cursos.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
