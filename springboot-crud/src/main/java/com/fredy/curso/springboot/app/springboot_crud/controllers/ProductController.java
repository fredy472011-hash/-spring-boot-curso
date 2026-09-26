package com.fredy.curso.springboot.app.springboot_crud.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.fredy.curso.springboot.app.springboot_crud.entities.Product;
import com.fredy.curso.springboot.app.springboot_crud.services.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



//@CrossOrigin(origins = "*") // en dev
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://127.0.0.1:5500") // <-- habilita frontend
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public List<Product> list(){
        return service.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        Optional<Product> productoOpcional = service.findById(id);
        if (productoOpcional.isPresent()) {
            return ResponseEntity.ok(productoOpcional.orElseThrow());
        } else {
            return ResponseEntity.notFound().build(); // se devuelve un 404
        }
        
    }

    @PostMapping("/post")
    public ResponseEntity<Product> create(@RequestBody Product producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(producto));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product producto) {
        Optional<Product> productoOptional = service.update(id, producto);
        if(productoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productoOptional.orElseThrow()); // el get del optional que devolvio el metodo
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Product> productoOpcional = service.delete(id);
        if (productoOpcional.isPresent()) {
            return ResponseEntity.ok(productoOpcional.orElseThrow());
        } else {
            return ResponseEntity.notFound().build(); // se devuelve un 404
        }
        
    }


}
