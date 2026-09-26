package com.fredy.curso.springboot.di.factura.springboot_difactura.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@RequestScope
/*@JsonIgnoreProperties({
        "targetSource", "advisors"
        ,"frozen", "proxiedInterfaces","targetClass",
        "advisorCount","preFiltered",
        "exposeProxy","proxyTargetClass","targetObject"
}) // para evitar el error de serializacion con request scope*/
public class Client {
    @Value("${client.name}")
    private String name;

    @Value("${client.lastname}")
    private String lastname;
    
    // se puede tener un atributo del tipo list con todas las facturas del cliente.
     public Client() {
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    //getter
    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }
}
