package com.springcloud.msvc.cursos.clients;

import com.springcloud.msvc.cursos.models.UserPOJO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UserClients {
    @GetMapping("/{id}")
    UserPOJO userDetail(@PathVariable Long id);
    @PostMapping("/")
    UserPOJO createUser(@RequestBody UserPOJO user);

}
