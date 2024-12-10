package com.applestore.applestore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Order;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomer(Customer customer);

	@org.springframework.data.jpa.repository.Query(value = "SELECT * FROM ORDERS", nativeQuery = true)
	List<Order> findAllOrder();

	@org.springframework.data.jpa.repository.Query(value = "SELECT * FROM ORDERS WHERE ORDERS.STATUS = ?1", nativeQuery = true)
	List<Order> listApprovedOrNotOrder(int status);

	@org.springframework.data.jpa.repository.Query(value = "UPDATE ORDERS SET ORDERS.STATUS = ?1 WHERE ORDERS.ORDER_ID = ?2", nativeQuery = true)
	void updateStatusOrder(int status, int id);

	@org.springframework.data.jpa.repository.Query(value = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?1", nativeQuery = true)
	List<Order> findByCustomerId_ASC(int customer_id);

	@org.springframework.data.jpa.repository.Query(value = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?1 ORDER BY ORDER_ID DESC", nativeQuery = true)
	List<Order> findByCustomerId_DESC(int customer_id);

	@Query(value = "SELECT COUNT(*) AS SoldProducts\n" +
			"FROM dbo.order_items\n" +
			"WHERE order_status = 2;", nativeQuery = true)
	long getTotalProductsSold();

	@Query(value = "SELECT SUM(oi.quantity * p.price) AS TotalRevenue " +
			"FROM dbo.orders o " +
			"INNER JOIN dbo.order_items oi ON o.order_id = oi.order_id " +
			"INNER JOIN dbo.products p ON oi.product_id = p.product_id " +
			"WHERE MONTH(o.order_date) = :month AND YEAR(o.order_date) = :year " +
			"AND oi.order_status = 2", nativeQuery = true)
	long getMonthlyRevenue(@Param("month") int month, @Param("year") int year);
}