package com.applestore.applestore.Repositories;


import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Entities.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select product_id, name, description, color, color_id, stock, CONVERT(INT, price) AS price, storage_capacity , img from products", nativeQuery = true)
    List<Product> findAllProduct();
    List<Product> findProductsByNameIgnoreCase(String search);
    List<Product> getProductsByColorIgnoreCase(String color);
    @Query(value = "select color from products group by color", nativeQuery = true)
    List<String> getAllColor();
    
    @Query(value = "SELECT * FROM products WHERE  name = ?1", nativeQuery = true)
    List<Product> dsMauCuaSanPham(String name);
    
    @Query(value = "SELECT * FROM products WHERE  name = ?1 and color = ?2", nativeQuery = true)
    Product LaySanPhamTheoMau(String name, String color);
    
    @Query(value = "SELECT p.name, p.color, p.storage_capacity, p.price FROM Product p", nativeQuery = true)
    List<Product> dataAllProducts();

    @Query(value = "SELECT SUM(CAST(stock AS int)) AS TotalProducts FROM products", nativeQuery = true)
    long getTotalProducts();
}
