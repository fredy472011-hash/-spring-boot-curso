package com.andres.curso.springboot.app.aop.springboot_aop.services;

import org.springframework.stereotype.Service;

@Service 
public class GreetingServiceImpl implements GreetingService{

    @Override
    public String sayHello(String person, String phrase) {
        String greeting = phrase + " " + person;
        return  greeting;
    }

    @Override
    public String sayHelloError() {
       throw new RuntimeException("Error de prueba de pointcut");
    }

    

}
