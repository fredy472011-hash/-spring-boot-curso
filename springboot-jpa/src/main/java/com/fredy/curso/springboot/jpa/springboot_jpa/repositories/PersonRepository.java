package com.fredy.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fredy.curso.springboot.jpa.springboot_jpa.entities.Person;
        /*
        ¿Y qué es @NoRepositoryBean?
        Es una anotación para indicar que esta interfaz no debe ser usada directamente como repositorio en tus entidades.
            Spring la usa como base, pero vos extendés de ella (CrudRepository, JpaRepository, etc.).
            List<T> lista = (List<T>) repo.findAll();
        */    
        //Para definir una interfaz de repositorio, primero debe definir una interfaz de repositorio específica para la clase de dominio.
        //El enfoque típico es extender CrudRepository, lo que proporciona métodos para la funcionalidad CRUD. CRUD significa Crear, Leer, Actualizar, Eliminar.
                                          // Tipo de dato y llave primaria tiene la interfaz
public interface PersonRepository extends CrudRepository<Person, Long>{
    // get y fetch tambien son palabras claves

    //Siempre recomienda optional porque envuelve al objeto
    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOne(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    // por detras realiza el like
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);
    Person findByName(String name);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    // ES UNA LISTA DE ARREGLOS DE OBJETOS PORQUE SON STRINGS o porque almacena a una persona completa, posicion 0 y 1,, 0{0,1}
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();


    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);

    

}
