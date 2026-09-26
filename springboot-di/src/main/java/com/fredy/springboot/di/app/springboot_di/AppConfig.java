package com.fredy.springboot.di.app.springboot_di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.fredy.springboot.di.app.springboot_di.repositories.IProductRepository;
import com.fredy.springboot.di.app.springboot_di.repositories.ProductRepositoryJson;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

        // se devuelve el tipo generico o interfaz, el concreto tiene el json
        @Bean(name = "productJson") // nombre del bean
        //@Primary
        IProductRepository productRepositoryJson() {
            // devuelve el tipo concreto, la clase puede ser una api externa
            return new ProductRepositoryJson();
        }
}
