package com.applestore.applestore.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applestore.applestore.DTOs.ProductReviewDto;
import com.applestore.applestore.DTOs.ShoppingCartDto;
import com.applestore.applestore.Entities.*;
import com.applestore.applestore.Repositories.ProductReviewRepository;
import com.applestore.applestore.Repositories.ShoppingCartRepository;



@Service
public class ProductReviewService {
	
	@Autowired
	private ProductReviewRepository productReviewRepository;
	
	@Autowired
	private CustomerService customerService;
	
	public void save(Customer customer, Product product, int rating, String review_text, LocalDateTime time) {
		ProductReview productReview = new ProductReview();
		productReview.setCustomer(customer);
		productReview.setProduct(product);
		productReview.setRating(rating);
		productReview.setReviewText(review_text);
		productReview.setEvaluation_time(time);
		
		productReviewRepository.save(productReview);
	}
	
	public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return localDateTime.format(formatter);
    }
	
	public List<ProductReviewDto> convertToDto(Customer customer){
		
		List<ProductReviewDto> productReviewDtos = new ArrayList<>();
		List<ProductReview> productReviews =  productReviewRepository.findByCustomer(customer);
		for (ProductReview productReview : productReviews){
			ProductReviewDto productReviewDto = new ProductReviewDto();
			productReviewDto.setRating(String.valueOf(productReview.getRating()));
			productReviewDto.setProductName(productReview.getProduct().getName());
			productReviewDto.setColor(productReview.getProduct().getColor());
			productReviewDto.setStorage_capacity(productReview.getProduct().getStorageCapacity());
			productReviewDto.setPrice(String.valueOf(productReview.getProduct().getPrice()));
			productReviewDto.setEvaluation_time(formatLocalDateTime(productReview.getEvaluation_time()));
			productReviewDtos.add(productReviewDto);
        }
		
		return productReviewDtos;
		
	}
}
