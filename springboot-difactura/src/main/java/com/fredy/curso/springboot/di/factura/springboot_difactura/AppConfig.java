package com.fredy.curso.springboot.di.factura.springboot_difactura;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.fredy.curso.springboot.di.factura.springboot_difactura.models.Item;
import com.fredy.curso.springboot.di.factura.springboot_difactura.models.Product;

@Configuration
@PropertySource(value = "classpath:data.properties", encoding = "UTF-8")
public class AppConfig {

    // @Primary
    @Bean
    List<Item> itemsInvoice(){
        Product p1 = new Product("Camara Sony", 800);
        Product p2 = new Product("Bicicleta Bianchi 26", 1200);
        // Es una lista de item no de product por eso en arrays as list no se podua product porque devuelve una lista de products, vaya
        return Arrays.asList(new Item(p1, 2), new Item(p2, 4)
        );
    }

    @Bean("default")
    List<Item> itemsInvoiceOficina(){
        Product p1 = new Product("Monitor Asus 24", 700);
        Product p2 = new Product("Notebook Razer", 2400);
        Product p3 = new Product("Impresora HP", 800);
        Product p4 = new Product("Escritorio de Oficina", 900);
        Product p5 = new Product("Teclado Gamer", 1000);
        Product p6 = new Product("Mouse Inalámbrico", 800);
        Product p7 = new Product("Monitor Xiaomi", 800);
        Product p8 = new Product("Monitor ViewSonic", 900);
        Product p9 = new Product("Silla Ergonómica", 2000);
        Product p10 = new Product("Impresora HP Mini", 900);
        // Es una lista de item no de product por eso en arrays as list no se podua product porque devuelve una lista de products, vaya
        return Arrays.asList(
            new Item(p1, 4), 
            new Item(p2, 6),
            new Item(p3, 1),
            new Item(p4, 4),
            new Item(p5, 2),
            new Item(p6, 3),
            new Item(p7, 1),
            new Item(p8, 2),
            new Item(p9, 1),
            new Item(p10, 1)
        );
    }

}
