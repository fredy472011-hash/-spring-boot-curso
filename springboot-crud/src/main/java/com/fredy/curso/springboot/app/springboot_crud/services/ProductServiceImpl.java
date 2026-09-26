package com.fredy.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fredy.curso.springboot.app.springboot_crud.entities.Product;
import com.fredy.curso.springboot.app.springboot_crud.repositories.ProductRepository;

import org.springframework.transaction.annotation.Transactional; // debe ser de spring el import

// logica de negocio
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    // Por constructor se pasa un componente o beans del contenedor. No es necesario usar en el constructor la anotacion autowired. Solo es necesario en metodo Setter o Atributo
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true) // lee datos 
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();    
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional // probable commit
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
       Optional<Product> productOptional = repository.findById(id);
       // se uso el if porque el lambda no devolvia nada
       if(productOptional.isPresent()){
            Product productDb = productOptional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setDescription(product.getDescription());
            return Optional.of(repository.save(productDb)); // el producto actualizado que se trajo de la bd
       }  
      return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(productDb ->{
            repository.delete(productDb);
        });
      return productOptional;
    }
    
}
