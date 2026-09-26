package com.fredy.springboot.di.app.springboot_di.repositories;

import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.fredy.springboot.di.app.springboot_di.models.Product;
// se instancia y se puede proveer a otros componentes, usa el patron Singleton
// Instancia unica para todos los demas componentes y clientes que se conectan a la app
@Repository("productList")
public class ProductRepository implements IProductRepository{
    private List<Product> data;

    public ProductRepository() {
        this.data = Arrays.asList(
            new Product(1L, "Memoria corsair 32", 300L),
            new Product(2L, "CPU Intel Core i9", 850L),
            new Product(3L, "Teclado Razer Mini 60%", 180L),
            new Product(4L, "Motherboard Gigabyte", 390L)
        );
    }

    @Override
    public List<Product> findAll() {
        return data;
    }
    
    @Override
    public Product findById(Long id) {
        return data.stream()
                   .filter(product -> product.getId().equals(id))
                   .findFirst()
                   .orElse(null);
    }
}
