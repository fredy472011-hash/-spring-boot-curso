package com.fredy.springboot.di.app.springboot_di.repositories;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.fredy.springboot.di.app.springboot_di.models.Product;

@Primary
@Repository("productJDBC")
public class ProductRepositoryJDBC implements IProductRepository{

    @Override
    public List<Product> findAll() {
        return Collections.singletonList(new Product(1L, "Monitor Asus 27", 600L));
    }

    @Override
    public Product findById(Long id) {
        return new Product(id, "Monitor Asus 27", 600L);
    }
    


}
// Aquí iría la implementación de la lógica para interactuar con la base de datos usando JDBC.
    // Por ejemplo, métodos para conectar a la base de datos, ejecutar consultas, etc.
    // Este repositorio podría implementar IProductRepository y proporcionar una implementación específica para JDBC.
    
    // Ejemplo de método:
    // public List<Product> findAll() {
    //     // Lógica para recuperar todos los productos desde la base de datos
    // }