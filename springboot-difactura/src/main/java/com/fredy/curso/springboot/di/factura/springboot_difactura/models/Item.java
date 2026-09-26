package com.fredy.curso.springboot.di.factura.springboot_difactura.models;

public class Item {
    private Product product;
    private Integer quantity;

    public Item() {
    }

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    // el importe por cada item del vector, el vector en i tiene product y quantity a nivel mayor, se multiplican y seguro sale al lado de quantity
    public int getImporte() {
        if (product == null || quantity == null) {
            return 0; // Manejo de caso donde product o quantity son nulos
        }
        return  quantity * product.getPrice();
    }

}
