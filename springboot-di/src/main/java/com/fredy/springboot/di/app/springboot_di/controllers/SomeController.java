package com.fredy.springboot.di.app.springboot_di.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fredy.springboot.di.app.springboot_di.models.Product;
import com.fredy.springboot.di.app.springboot_di.services.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")    
// Por defecto es una Scop Singleton, Una Instancia compartida por toda la aplicacion y usuarios
public class SomeController {
    // Una instancia, Repositorio en memoria para todos los clientes. Pero si es de BD no viene de la memoria
    //nosotros no tenemos que instanciar service ni productoService, Spring nos provee ese objeto o instancia mediante el patron Singleton. Una instancia para todas las clases
    // Es como composicion
    //Polimorfismo por la interfaz e implemnentacion
    //Inyecta mediante la interfaz
   // @Autowired
    private IProductService service;             //private ProductService service = new ProductService();
    //@Autowired
    public SomeController(IProductService service) { // se saco la anotacion en el controlador
        this.service = service;
    }

    @GetMapping()
    public List<Product> list(){
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Product show(@PathVariable Long id){
        return service.findById(id);
    }


}
