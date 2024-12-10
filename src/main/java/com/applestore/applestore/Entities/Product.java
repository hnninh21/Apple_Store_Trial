package com.applestore.applestore.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description",columnDefinition = "nvarchar(max)", nullable = false)
    private String description;

    @Column(name = "img", columnDefinition = "varchar(max)", nullable = false)
    private String img;

    @Column(name = "stock", nullable = false)
    private String stock;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "color", columnDefinition = "nvarchar(10)", nullable = false)
    private String color;
    
    @Column(name = "color_id", nullable = false)
    private String colorId;



    @Column(name = "storage_capacity", nullable = true)
    private String storageCapacity;

    // Constructors, getters, and setters
    public Product() {
    }

    

    public Product(int productId, String name, String description, String img, String stock, int price, String color,
			String colorId, String storageCapacity) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.img = img;
		this.stock = stock;
		this.price = price;
		this.color = color;
		this.colorId = colorId;
		this.storageCapacity = storageCapacity;
	}



	public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }



	public String getColorId() {
		return colorId;
	}



	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
    
    
}