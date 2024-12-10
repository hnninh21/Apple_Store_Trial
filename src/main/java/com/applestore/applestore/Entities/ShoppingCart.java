package com.applestore.applestore.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "shopping_cart")

public class ShoppingCart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Constructors, getters, and setters
    public ShoppingCart() {
    }

    public ShoppingCart(Customer customer, Product product, int quantity) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
