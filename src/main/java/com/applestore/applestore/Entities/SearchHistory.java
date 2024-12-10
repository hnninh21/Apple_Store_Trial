package com.applestore.applestore.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "search_query", nullable = false)
    private String searchQuery;
    
    @Column(name = "search_date", nullable = false)
    private LocalDateTime search_date;
    
    // Constructors, getters, and setters
    public SearchHistory() {
    }

    

    public SearchHistory(int id, Customer customer, String searchQuery, LocalDateTime search_date) {
		super();
		this.id = id;
		this.customer = customer;
		this.searchQuery = searchQuery;
		this.search_date = search_date;
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

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }



	public LocalDateTime getSearch_date() {
		return search_date;
	}



	public void setSearch_date(LocalDateTime search_date) {
		this.search_date = search_date;
	}
    
    
    
}