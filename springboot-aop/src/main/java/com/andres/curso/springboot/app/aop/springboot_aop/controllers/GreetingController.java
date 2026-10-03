package com.andres.curso.springboot.app.aop.springboot_aop.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.andres.curso.springboot.app.aop.springboot_aop.services.GreetingService;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
public class GreetingController {

    // va a inyectar el servicio
    private final GreetingService greetingService;


    //Desde Spring 4.3, si la clase tiene un solo constructor no hace falta @Autowired: Spring lo detecta solo.
    public GreetingController(GreetingService greetingService){
        this.greetingService = greetingService;
    }


    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(@RequestParam  String nombre, @RequestParam String mensaje) {
        // un map es un colection
        return ResponseEntity.ok(Collections.singletonMap("greeting",
            greetingService.sayHello(nombre, mensaje)
        ));
    }
    

}
