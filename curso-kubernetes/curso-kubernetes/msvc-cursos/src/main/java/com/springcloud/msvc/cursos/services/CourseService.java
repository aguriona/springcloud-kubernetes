package com.springcloud.msvc.cursos.services;

import com.springcloud.msvc.cursos.entities.Course;
import com.springcloud.msvc.cursos.models.UserPOJO;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAllCourses();
    Optional<Course> findCourseById(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);

    Optional<UserPOJO> assignUser(UserPOJO user, Long idCourse);
    Optional<UserPOJO> quitUser(UserPOJO user, Long idCourse);
    Optional<UserPOJO> createUserCourse(UserPOJO user, Long idCourse);

}
