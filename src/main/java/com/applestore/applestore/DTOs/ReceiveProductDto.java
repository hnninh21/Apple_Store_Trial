package com.applestore.applestore.DTOs;

import org.springframework.data.relational.core.sql.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Data

@Setter
@Getter
public class ReceiveProductDto {
	private String name, color, price, storage_capacity;
}
