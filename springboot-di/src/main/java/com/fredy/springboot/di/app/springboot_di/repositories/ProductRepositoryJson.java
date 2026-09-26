package com.fredy.springboot.di.app.springboot_di.repositories;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredy.springboot.di.app.springboot_di.models.Product;

public class ProductRepositoryJson implements IProductRepository {

    private List<Product> list;
    
    public ProductRepositoryJson(){
        // se nombro primero a la carpeta de ubicacion
        Resource resource = new ClassPathResource("json/product.json");
        ObjectMapper o = new ObjectMapper();
        // devuelve una lista del arreglo que se obtuvo de config y es de clase product
        try {
            list = Arrays.asList(o.readValue(resource.getFile(), Product[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        return list; 
    }

    @Override
    public Product findById(Long id) {
        //por defecto la lambda tiene el return no se escribe
        // porque es una instruccion se omite {} en lambda
        return list.stream()
                   .filter(p -> p.getId().equals(id))
                   .findFirst()
                   .orElse(null);
    }
}
