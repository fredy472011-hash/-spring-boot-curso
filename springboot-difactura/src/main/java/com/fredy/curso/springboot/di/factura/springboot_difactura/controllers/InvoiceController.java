package com.fredy.curso.springboot.di.factura.springboot_difactura.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fredy.curso.springboot.di.factura.springboot_difactura.models.Client;
import com.fredy.curso.springboot.di.factura.springboot_difactura.models.Invoice;
import com.fredy.curso.springboot.di.factura.springboot_difactura.models.Persona;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/invoices")    
public class InvoiceController {

    private Invoice invoice;

    //constructor
    public InvoiceController(Invoice invoice) {
        this.invoice = invoice;
    }

    @GetMapping("/show")
    public ResponseEntity<Object> show(){
        if (invoice == null) {
            return ResponseEntity.notFound().build(); 
        }
        Invoice i = new Invoice();
        Client c = new Client();
        c.setName(invoice.getClient().getName());
        c.setLastname(invoice.getClient().getLastname());
        i.setClient(c);
        i.setDescription(invoice.getDescription());
        i.setItems(invoice.getItems());
        return ResponseEntity.ok(i);
    }

    @PostMapping(value = "/guardar",
             consumes = "application/json",
             produces = "application/json")
    public ResponseEntity<Persona> postMethodName(@RequestBody Persona persona, HttpSession session) {
        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setApellido(persona.getApellido().toUpperCase());  
        // guardo en la sesion sus datos
        session.setAttribute("ultimaPersona", persona);
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/mostrar")
    public ResponseEntity<Persona> mostrar(HttpSession session) {
        Persona p = (Persona) session.getAttribute("ultimaPersona");
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    
    

}
