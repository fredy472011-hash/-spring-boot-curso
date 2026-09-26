package com.fredy.curso.springboot.di.factura.springboot_difactura.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

// Invoice es de tipo singleton
@Component
@RequestScope // or SessionScope solo que el contexto es mas amplio
/*@JsonIgnoreProperties({
        "targetSource", "advisors"
        ,"frozen", "proxiedInterfaces","targetClass",
        "advisorCount","preFiltered",
        "exposeProxy","proxyTargetClass","targetObject"
}) // para evitar el error de serializacion con request scope*/
public class Invoice {
    // se quiere saber de la factura el cliente y la linea de item que tiene producto asociado
    @Autowired
    private Client client;
    //description
    @Value("${invoice.description.office}")
    private String description;
    //list items

    @Autowired // tiene precio, cantidad y total la multiplicacion con el metodo anotado con get
    @Qualifier("default")
    private List<Item> items;
    
    public Invoice() {
        System.out.println("Se creo el constructor!");
        //try{
          //   System.out.println(client.getName().concat(" ").concat(client.getLastname())); // no existe aun
        //}catch(Exception e){
            System.out.println(client);
        //}
       
    }

    @PostConstruct
    public void init(){
        // Se modifico el dato luego de inyectarlo.
        System.out.println("Creando el componente de la factura");
        System.out.println("Antes: " +client.getName().concat(" ").concat(client.getLastname()));// Va a modificarlo
        client.setName(client.getName().concat(" Leonardo"));
        client.setLastname(client.getLastname().concat(" DiCaprio"));
        System.out.println("Después: " +client.getName().concat(" ").concat(client.getLastname()));
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Factura destruida!");
        System.out.println(".................");
    }
    
    //get and set
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        if(this.items == null || this.items.isEmpty()) {
            return 0; // Manejo de caso donde items es nulo o vacío
        }
        /*
            items.stream() → convierte la lista de Item en un **Stream<Item>`.
            .mapToInt(Item::getImporte) → transforma cada Item en un int (su importe). Ahora es un IntStream.
            .sum() → suma todos esos int.
         */
        return items.stream()
                .mapToInt(Item::getImporte)
                .sum();
        /*
         Otra forma
            items.stream() → igual que antes.
            .map(...) → convierte cada Item en su importe (Stream<Integer>).
            .reduce(0, (sum, importe) -> sum + importe)
            0 es el valor inicial.
            (sum, importe) -> sum + importe es una función acumuladora: va sumando cada importe.
        ✔️ Es un poco más general, útil si estás trabajando con objetos (Stream<T>) y querés hacer más lógica dentro del reduce.
            int total = items.stream().map(item -> item.getImporte()).reduce(0,(sum, importe) -> sum + importe);
            return total;

        Madre mia esta tercera forma, va filtrando
        ¿Qué hace?
            items.stream()
                → Stream de todos los Item.
            .filter(...)
                → Filtra: solo deja pasar los Item cuyo product.getPrice() sea mayor a 100.
            .mapToInt(Item::getImporte)
                → Convierte cada Item filtrado a su importe (cantidad * precio).
            .sum()
                → Suma los importes filtrados.
                
                return items.stream()
                            .filter(item -> item.getProduct().getPrice() > 100) // solo los caros
                            .mapToInt(Item::getImporte)
                            .sum();

        Ultima forma
                return items.stream()
                .filter(item -> item.getProduct().getPrice() > 100)
                .map(Item::getImporte)
                .reduce(0, Integer::sum);
        Porque es más limpio y legible que escribir la función completa:    
            .reduce(0, (a, b) -> a + b)
        se escribe:
            .map(Item::getImporte)
            .reduce(0, Integer::sum);
        significa:
            ➡️ "Convertí cada Item en su importe, y sumalos todos empezando desde 0".
         */
    }

}
