package com.applestore.applestore.Repositories;


import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

	@Query(value = "SELECT * FROM shopping_cart WHERE  customer_id = ?1 ORDER BY id DESC", nativeQuery = true)
    List<ShoppingCart> findByCustomer(int customerId);
}
