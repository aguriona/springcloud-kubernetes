package com.springcloud.msvc.cursos.entities;

import com.springcloud.msvc.cursos.models.UserPOJO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<UserPOJO> userPOJOList;

    public Course() {
        courseUsers = new ArrayList<>();
    }
    public void addCourseUsers(CourseUser courseUser){
        courseUsers.add(courseUser);
    }
    public void removeCourseUsers(CourseUser courseUser){
        courseUsers.remove(courseUser);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }
}
