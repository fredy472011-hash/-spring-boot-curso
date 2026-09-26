package com.andres.curso.springboot.error.springboot_error.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.andres.curso.springboot.error.springboot_error.exceptions.UserNotFoundException;
import com.andres.curso.springboot.error.springboot_error.models.domain.User;
import com.andres.curso.springboot.error.springboot_error.services.UserService;

@RestController
@RequestMapping("/app")
public class AppController {
    
    // usa la interfaz
    private UserService service;

    public AppController( UserService service) {
        this.service = service;
    }

    @GetMapping
    public String index() {
        // dos errores
        //var value = 100/0;
        //System.out.println(value);
        int value = Integer.parseInt("10x");
        System.out.println();
        System.out.println("VALOR -> " +value);
        System.out.println();
        return "ok 200";
    }
    //@RequestParam y @PathVariable. OJO
    @GetMapping("/show/{id}")
    public User show(@PathVariable(name = "id") Long id) {
        // crea una excepcion personalizada que llama al constructor del padre. Maneja el error 500 y lo captura con nuestra clase HandlerExceptionController
        User user = service.findById(id)
            .orElseThrow(() -> new UserNotFoundException("No existe el usuario con ID: " + id));
        return user;
        //Optional<User> optionalUser = service.findById(id);
        //if(optionalUser.isEmpty()){
          //  return ResponseEntity.notFound().build();           
        //}
        //return ResponseEntity.ok(optionalUser.orElseThrow());
    }
    

}
