package com.applestore.applestore.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

      
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "address_line", columnDefinition = "nvarchar(300)")
    private String addressLine;
    // Constructors, getters, and setters
    public Order() {
    }

    

    public Order(int orderId, Customer customer, LocalDateTime orderDate, String addressLine) {
		super();
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.addressLine = addressLine;
	}



	public int getOrderId() {
		return orderId;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public LocalDateTime getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}



	public String getAddressLine() {
		return addressLine;
	}



	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}



	
    
}