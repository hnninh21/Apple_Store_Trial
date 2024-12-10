package com.applestore.applestore.DTOs;
import org.springframework.data.relational.core.sql.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class ProductReviewDto {
	private String rating, price, productName, color, storage_capacity, evaluation_time;
	
}
