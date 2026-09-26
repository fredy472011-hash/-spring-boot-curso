package com.fredy.springboot.di.app.springboot_di.repositories;

import java.util.List;

import com.fredy.springboot.di.app.springboot_di.models.Product;
// Muchos mas repositorios pueden implementar este contrato de diversas formas
public interface IProductRepository {
    //Porque es una interfaz se ve asi el metodo
    List<Product> findAll();
    Product findById(Long id);  
}
