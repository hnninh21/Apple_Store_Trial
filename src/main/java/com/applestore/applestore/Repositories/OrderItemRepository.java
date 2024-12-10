package com.applestore.applestore.Repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.applestore.applestore.Entities.Order;
import com.applestore.applestore.Entities.OrderItem;
import java.util.List;




@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	
	List<OrderItem> findByOrder(Order order);
	List<OrderItem> findByOrderStatus(int orderStatus);
}