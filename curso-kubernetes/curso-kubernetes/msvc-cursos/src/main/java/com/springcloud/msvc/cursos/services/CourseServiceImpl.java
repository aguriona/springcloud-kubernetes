package com.springcloud.msvc.cursos.services;

import com.springcloud.msvc.cursos.clients.UserClients;
import com.springcloud.msvc.cursos.entities.Course;
import com.springcloud.msvc.cursos.entities.CourseUser;
import com.springcloud.msvc.cursos.models.UserPOJO;
import com.springcloud.msvc.cursos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserClients userClients;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UserPOJO> assignUser(UserPOJO user, Long idCourse) {
        Optional<Course> courseOptional = courseRepository.findById(idCourse);
        if (courseOptional.isPresent()) {
            UserPOJO userPOJO = userClients.userDetail(user.getId());
            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userPOJO.getId());
            course.addCourseUsers(courseUser);
            courseRepository.save(course);
            return Optional.of(userPOJO);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserPOJO> quitUser(UserPOJO user, Long idCourse) {
        Optional<Course> courseOptional = courseRepository.findById(idCourse);
        if (courseOptional.isPresent()) {
            UserPOJO userPOJO = userClients.userDetail(user.getId());
            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userPOJO.getId());
            course.removeCourseUsers(courseUser);
            courseRepository.save(course);
            return Optional.of(userPOJO);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserPOJO> createUserCourse(UserPOJO user, Long idCourse) {
        Optional<Course> courseOptional = courseRepository.findById(idCourse);
        if (courseOptional.isPresent()) {
            UserPOJO newUserPOJO = userClients.createUser(user);
            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUserPOJO.getId());
            course.addCourseUsers(courseUser);
            return Optional.of(newUserPOJO);
        }

        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseByUsers(Long id) {
        Optional<Course> courseOptional = findCourseById(id);
        if (courseOptional.isPresent()) {
            Course curso = courseOptional.get();
            if (!curso.getCourseUsers().isEmpty()) {
                List<Long> ids = curso.getCourseUsers()
                        .stream().map(curseUser -> curseUser.getUserId())
                        .collect(Collectors.toList());
                List<UserPOJO> usuarios = userClients.usersByCourses(ids);
                curso.setUserPOJOList(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCurseUserById(Long id) {
        courseRepository.deleteCourseUserById(id);
    }
}
