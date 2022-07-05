package com.springcloud.msvc.usuarios.services;

import com.springcloud.msvc.usuarios.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);

    List<User> findAllusersById(Iterable<Long> ids);
    Optional<User> findUserByEmail(String email);
}
