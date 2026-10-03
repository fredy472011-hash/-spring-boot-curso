package com.andres.curso.springboot.app.aop.springboot_aop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect 
@Component
public class GreetingAspect {

    private static final Logger logger = LoggerFactory.getLogger(GreetingAspect.class);

    @Before("execution(* *..GreetingService.sayHello(..))")
    public void loggerBefore(JoinPoint joinPoint){

        String method = joinPoint.getSignature().getName(); 
        String args   = Arrays.toString(joinPoint.getArgs());
        logger.info(
            "Antes: " + method + " con los argumentos " + args
        );

    }

    // aplica a cualquier metodo de la interfaz, siempre se ejecuta se lance o no una excepcion
    @After("execution(* *..GreetingService.*(..))")
    public void loggerAfter(JoinPoint joinPoint){

        String method = joinPoint.getSignature().getName(); 
        String args   = Arrays.toString(joinPoint.getArgs());
        logger.info(
            "Despues: " + method + " con los argumentos " + args
        );
    }

    @AfterReturning("execution(* *..GreetingService.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint){

        String method = joinPoint.getSignature().getName(); 
        String args   = Arrays.toString(joinPoint.getArgs());
        logger.info(
            "Despues de retornar: " + method + " con los argumentos " + args
        );
    }

    @AfterThrowing("execution(* *..GreetingService.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint){

        String method = joinPoint.getSignature().getName(); 
        String args   = Arrays.toString(joinPoint.getArgs());
        logger.info(
            "Despues de lanzar la excepcion: " + method + " con los argumentos " + args
        );
    }

    @Around("execution(* *..GreetingService.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // va a capturar lo que devuelve el metodo del service
        Object result = null;
        try{
            // before del metodo
            logger.info("El metodo "+ method +"() con los parametros " + args);
            result = joinPoint.proceed();
            // After
            logger.info("El metodo " + method +"() retorna el resultado "+ args);
            return result;
        }catch(Throwable e){
            logger.error("Error en la llamada del metodo " + method + "() ", e);
            throw e;
        }
    }

}
