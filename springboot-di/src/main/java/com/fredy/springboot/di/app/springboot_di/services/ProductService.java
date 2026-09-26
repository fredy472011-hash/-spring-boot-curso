package com.fredy.springboot.di.app.springboot_di.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.fredy.springboot.di.app.springboot_di.models.Product;
import com.fredy.springboot.di.app.springboot_di.repositories.IProductRepository;

//Ya tiene su generico y se puede implementar otro producto servicio
@Service // El service maneja la logica de negocio
 // el contenedor nos pasa una instancia que esta guardada en el contenedor
    //private ProductRepository productRepository= new ProductRepository();
    // tambien se lo llama autocableado. Esto se conoce como el principio Hollywood, no nos llames, nosotros te llamaremos
//@Autowired // Por constructor se pasa un componente o beans del contenedor. No es necesario usar en el constructor la anotacion autowired. Solo es necesario en metodo Setter o Atributo
    //Construccion por Setter
   // @Autowired // Sin esto falla Java.lang.NullPointerException
   // public void setProductRepository(IProductRepository productRepository) {
     //   this.productRepository = productRepository;
   // }

   // public List<Product> findAll() {
     // creo que map es para todas las instancias
        // El service se tiene que inyectar en el controlador, no es recomendable utilizar el repository(solo se persisten los datos) en el controller directamente
            // Nueva instancia distinta a la original. Se cumple el principio de inmutabilidad
            // Nueva instancia a partir de la original
            //Product newProduct = new Product(product.getId(), product.getName(), priceImp.longValue());
            // Entonces cada cliente tiene una nueva lista de producto nuevo, y la instancia unica no se toca
            // Se respeta el principio de inmutabiliad, no cambiar el objeto que reside en memoria


public class ProductService implements IProductService {
    private Environment environment;

    private IProductRepository productRepository;
    
    @Value("${config.price.tax}")
    private Double tax;//polimorfismo
    
    public ProductService(@Qualifier("productJson") IProductRepository productRepository, Environment environment) {
        this.productRepository = productRepository;
        this.environment = environment;
    }

    @Override 
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(product -> {
            Double priceTax;
            try {
                priceTax = product.getPrice() * environment.getProperty("config.price.tax", Double.class);
                System.out.println(environment.getProperty("config.price.tax", Double.class));
                System.out.println("Tax: " + tax);
            } catch (Exception e) {
                priceTax = product.getPrice() * 1.25d;
            }

            Product newProduct = (Product) product.clone();
            newProduct.setPrice(priceTax.longValue());
            return newProduct;
        }).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

}
