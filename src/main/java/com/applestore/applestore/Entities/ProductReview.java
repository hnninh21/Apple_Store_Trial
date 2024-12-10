package com.applestore.applestore.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "product_reviews")
public class ProductReview {

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

    @Column(name = "rating", nullable = false)
    private int rating; 

    @Column(name = "review_text", columnDefinition = "nvarchar(max)")
    private String reviewText;
    
    @Column(name = "evaluation_time", nullable = false)
    private LocalDateTime evaluation_time;

    // Constructors, getters, and setters
    public ProductReview() {
    }

    
    public ProductReview(int id, Customer customer, Product product, int rating, String reviewText,
			LocalDateTime evaluation_time) {
		
		this.id = id;
		this.customer = customer;
		this.product = product;
		this.rating = rating;
		this.reviewText = reviewText;
		this.evaluation_time = evaluation_time;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }


	public LocalDateTime getEvaluation_time() {
		return evaluation_time;
	}


	public void setEvaluation_time(LocalDateTime evaluation_time) {
		this.evaluation_time = evaluation_time;
	}
    
    
}
