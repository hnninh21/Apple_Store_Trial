package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.ProductReview;
import com.applestore.applestore.Entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

	List<ProductReview> findByCustomer(Customer customer);
	
	
}