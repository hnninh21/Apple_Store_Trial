package com.applestore.applestore.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class SearchHistoryDto {
	private String search_query, search_date;
	
}
