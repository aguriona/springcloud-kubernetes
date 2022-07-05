package com.springcloud.msvc.cursos.clients;

import com.springcloud.msvc.cursos.models.UserPOJO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UserClients {
    @GetMapping("/{id}")
    UserPOJO userDetail(@PathVariable Long id);
    @PostMapping("/")
    UserPOJO createUser(@RequestBody UserPOJO user);
    @GetMapping("/users-by-courses")
    List<UserPOJO> usersByCourses(@RequestParam Iterable<Long> ids);

}
