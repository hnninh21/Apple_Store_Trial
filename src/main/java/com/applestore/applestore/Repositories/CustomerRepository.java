package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT COUNT(DISTINCT c.customer_id) AS TotalCustomers\n" +
            "FROM dbo.customers c\n" +
            "INNER JOIN dbo.users_roles ur ON c.user_id = ur.user_id\n" +
            "WHERE ur.role_id = 1;", nativeQuery = true)
    long getTotalCustomers();

}
