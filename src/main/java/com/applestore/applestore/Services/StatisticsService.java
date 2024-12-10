package com.applestore.applestore.Services;

import com.applestore.applestore.Repositories.CustomerRepository;
import com.applestore.applestore.Repositories.OrderRepository;
import com.applestore.applestore.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    public long getTotalCustomers() {
        return customerRepository.getTotalCustomers();
    }

    public long getTotalProducts() {
        return productRepository.getTotalProducts();
    }

    public long getTotalProductsSold() {
        return orderRepository.getTotalProductsSold();
    }

    public long getMonthlyRevenue(int month, int year) {return orderRepository.getMonthlyRevenue(month, year);}
}
