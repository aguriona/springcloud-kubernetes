package com.springcloud.msvc.cursos.controllers;

import com.springcloud.msvc.cursos.entities.Course;
import com.springcloud.msvc.cursos.models.UserPOJO;
import com.springcloud.msvc.cursos.services.CourseService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> listCourses(){
        return courseService.findAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCourse(@PathVariable Long id){
        Optional<Course> courseOptional = courseService.findCourseById(id);
        if (courseOptional.isPresent()){
            return ResponseEntity.ok().body(courseOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        try {
            return ResponseEntity.created(null).body(courseService.saveCourse(course));

        } catch (Error e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Course> courOptional = courseService.findCourseById(id);
        if (courOptional.isPresent()){
            courOptional.get().setName(course.getName());
            return ResponseEntity.ok().body(courseService.saveCourse(courOptional.get()));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        if (courseService.findCourseById(id).isPresent()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody UserPOJO user, @PathVariable Long courseId){
        Optional<UserPOJO> userOpt = null;
        try{
            userOpt = courseService.assignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.internalServerError().build();
        }
        if(userOpt.isPresent()){
            return ResponseEntity.created(null).body(userOpt);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/quit-user/{userId}")
    public ResponseEntity<?> quitUser(@PathVariable Long userId){
        Optional<UserPOJO> userOpt = courseService.quitUser().assignUser(user, courseId);
        if(userOpt.isPresent()){
            return ResponseEntity.created(null).body(userOpt);
        }
        return ResponseEntity.badRequest().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }
}
