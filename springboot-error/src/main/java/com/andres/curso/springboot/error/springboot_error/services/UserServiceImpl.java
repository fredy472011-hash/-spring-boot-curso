package com.andres.curso.springboot.error.springboot_error.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.andres.curso.springboot.error.springboot_error.models.domain.Role;
import com.andres.curso.springboot.error.springboot_error.models.domain.User;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        // registrar en un componente config y inyectarlo, tiene anotacion bean
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        this.users = List.of(
            new User(1L, "Andres", "Gonzalez", roleAdmin),
            new User(2L, "John", "Doe", roleUser),
            new User(3L, "Jane", "Doe", roleUser),
            new User(4L, "Mary", "Smith", roleUser),
            new User(5L, "Peter", "Johnson", roleUser)
        );

    }

    @Override
    public List<User> findAll() {
        return users;
    }
    @Override
    public Optional<User> findById(Long id) {
        if(this.users.isEmpty()){
            return Optional.empty(); // si esta vacia retorna un optional vacio
        }
        User user = this.users.stream().filter(users -> (users.getId().equals(id))).findFirst().orElse(null);
        return Optional.ofNullable(user); // si user es null retorna un optional vacio
    }

}
