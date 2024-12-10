package com.applestore.applestore.Entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "order_status")
    private int orderStatus;
    
    @Column(name = "note", columnDefinition = "nvarchar(255)", nullable = true)
    private String note;
    
    @Column(name = "rating_status")
    private int ratingStatus;
    
    // Constructors, getters, and setters
    public OrderItem() {
    }

    

    



	public OrderItem(int orderItemId, Order order, Product product, int quantity, int orderStatus, String note,
			int ratingStatus) {
		
		this.orderItemId = orderItemId;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		this.note = note;
		this.ratingStatus = ratingStatus;
	}


	public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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



	public int getOrderStatus() {
		return orderStatus;
	}



	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}



	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public int getRatingStatus() {
		return ratingStatus;
	}


	public void setRatingStatus(int ratingStatus) {
		this.ratingStatus = ratingStatus;
	}
    
    
}
