package com.applestore.applestore.Services;
import java.io.*;

import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.DTOs.ReceiveProductDto;
import com.applestore.applestore.DTOs.ColorDto;
import com.applestore.applestore.Entities.Product;

import com.applestore.applestore.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductDto convertProductToDto(Product product){
        ProductDto productDto = new ProductDto();
       
        productDto.setProduct_id(product.getProductId());
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setStock(Integer.parseInt(product.getStock()));
        productDto.setPrice(formatCurrency(product.getPrice()));
        productDto.setColor(product.getColor());
        productDto.setImg(product.getImg());
        return productDto;
    }
    
    public ProductDto getProductByColor(int productId, String color){
    	
    	Product product = productRepository.getReferenceById(productId);
    	product = productRepository.LaySanPhamTheoMau(product.getName(), color);
        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getProductId());
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setStock(Integer.parseInt(product.getStock()));
        productDto.setPrice(formatCurrency(product.getPrice()));
        productDto.setColor(product.getColor());
        productDto.setColorId(product.getColorId());
        productDto.setImg(product.getImg());
        return productDto;
    }
    
    public List<ColorDto> getColorDtosForProduct(int productId) {
    	Product product = productRepository.getReferenceById(productId);
    	List<Product> products = productRepository.dsMauCuaSanPham(product.getName());
    	List<ColorDto> colorDtos = new ArrayList<>();
    	for (Product prod : products){
            ColorDto colorDto = new ColorDto();
            colorDto.setColor(prod.getColor());
            colorDto.setColorId(prod.getColorId());
            
            colorDtos.add(colorDto);
        }
        return colorDtos;
    }

    public Product convertProductDtoToEntity(ProductDto productDto){
        Product product = new Product();
        product.setProductId(productDto.getProduct_id());
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setStock(String.valueOf(productDto.getStock()));
        product.setPrice(Integer.parseInt(productDto.getPrice()));
        product.setColorId(productDto.getColorId());
        product.setStorageCapacity(productDto.getStorageCapacity());
        product.setColor(productDto.getColor());
        product.setImg(productDto.getImg());
        System.out.println(product.getImg());
        return product;
    }
    
    
    
    public String encodingImage(MultipartFile multipartFile){
        String base64Image = null;
        try {
            base64Image = Base64.getEncoder().encodeToString(multipartFile.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        return base64Image;
    }

    public List<ProductDto> recommendedProducts(List<ReceiveProductDto> receiveProductDto){
        List<ProductDto> listAllProduct = new ArrayList<>();
        for(ReceiveProductDto pro : receiveProductDto){
        	Product product = findProductByNameAndcolor(pro.getName(), pro.getColor());
            listAllProduct.add(convertProductToDto(product));
        }
        return listAllProduct;
    }
    
    public Product findProductByNameAndcolor(String name, String color){
    	Product product = productRepository.LaySanPhamTheoMau(name, color) ;
                  
        return product;
    }
    
    public List<ProductDto> listALlProductDTo(){
        List<ProductDto> listAllProduct = new ArrayList<>();
        for(Product pro : productRepository.findAllProduct()){
            listAllProduct.add(convertProductToDto(pro));
        }
        return listAllProduct;
    }
    
    public List<Product> listALlProduct(){
    	List<Product> products = productRepository.findAllProduct() ;
                  
        return products;
    }
    
    


    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Product getProductById(int id){
        return productRepository.getReferenceById(id);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public List<ProductDto> findProductByName(String search){
        List<ProductDto> listResult = new ArrayList<>();
        for (ProductDto proDto : listALlProductDTo()){
            if (proDto.getName().toLowerCase().contains(search.toLowerCase())){
                listResult.add(proDto);
            }
        }
//        for (Product pro : productRepository.findProductsByNameIgnoreCase(search)){
//            listResult.add(convertProductToDto(pro));
//        }
        return listResult;
    }

    public List<ProductDto> getAllOrderByPrice(Boolean condition){
        List<ProductDto> listProductOrderByPrice = listALlProductDTo();
        if (condition){
            Comparator<ProductDto> comparator = new Comparator<ProductDto>() {
                @Override
                public int compare(ProductDto o1, ProductDto o2) {
                    return o1.getIntPrice() - o2.getIntPrice();
                }
            };
            listProductOrderByPrice.sort(comparator);
        } else {
            Comparator<ProductDto> comparator = new Comparator<ProductDto>() {
                @Override
                public int compare(ProductDto o1, ProductDto o2) {
                    return o2.getIntPrice() - o1.getIntPrice();
                }
            };
            listProductOrderByPrice.sort(comparator);
        }
        for (ProductDto productDto : listProductOrderByPrice){
            System.out.println("Price at order func: " + productDto.getPrice());
        }
        return listProductOrderByPrice;
    }

    public List<String> getAllColor(){
        List<String> listColor = productRepository.getAllColor();
        System.out.println("List color: ");
        for (String color : listColor){
            System.out.println(color);
        }
        return listColor;
    }

    public List<ProductDto> getProductByColor(String color){
        List<ProductDto> listProductByColor = new ArrayList<>();
        for (Product product : productRepository.getProductsByColorIgnoreCase(color)){
            listProductByColor.add(convertProductToDto(product));
        }
        return listProductByColor;
    }

    public String formatCurrency(int price){
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(price) + "₫";
    }

    public int convertToInt(String price) {
        String stringPrice = price.replace(".", "").replace(" ", "").replace("₫", "").replace(",", "");
        int intPrice = Integer.parseInt(stringPrice);
        System.out.println(stringPrice + "Check");
        return intPrice;
    }
    
    public List<Product> dataAllProducts(){
    	List<Product> products = new ArrayList<>();
    	products = productRepository.dataAllProducts(); 
    	
    	return products;
    }
}
