package com.springcloud.msvc.usuarios.controllers;

import com.springcloud.msvc.usuarios.entities.User;
import com.springcloud.msvc.usuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> listAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public  ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result){
        if(!user.getEmail().isEmpty() && userService.findUserByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Error","E-mail already exist"));
        }
        if(result.hasErrors()){
            return validar(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<User> userUpdated = userService.findUserById(id);
        if (userUpdated.isPresent()){
            userUpdated.get().setName(user.getName());
            userUpdated.get().setEmail(user.getEmail());
            userUpdated.get().setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userUpdated.get()));
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if(userService.findUserById(id).isPresent()){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

}
