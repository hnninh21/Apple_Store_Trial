package com.applestore.applestore.Services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applestore.applestore.DTOs.ProductReviewDto;
import com.applestore.applestore.DTOs.SearchHistoryDto;
import com.applestore.applestore.Entities.*;
import com.applestore.applestore.Repositories.SearchHistoryRepository;



@Service
public class SearchHistoryService {
	
	@Autowired
	private SearchHistoryRepository historyRepository ;
	
	@Autowired
	private CustomerService customerService;
	
	public void save(Customer customer, String search_query) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		SearchHistory searchHistory = new SearchHistory();
		searchHistory.setCustomer(customer);
		searchHistory.setSearchQuery(search_query);
		searchHistory.setSearch_date(currentDateTime);
		historyRepository.save(searchHistory);
	}
	
	public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return localDateTime.format(formatter);
    }
	
	public List<SearchHistoryDto> convertToDto(Customer customer){
		
		List<SearchHistoryDto> searchHistoryDtos = new ArrayList<>();
		List<SearchHistory> searchHistories =  historyRepository.findByCustomer(customer);
		for (SearchHistory searchHistory : searchHistories){
			SearchHistoryDto searchHistoryDto = new SearchHistoryDto();
			searchHistoryDto.setSearch_date(formatLocalDateTime(searchHistory.getSearch_date()));
			searchHistoryDto.setSearch_query(searchHistory.getSearchQuery());
			searchHistoryDtos.add(searchHistoryDto);
        }
		
		return searchHistoryDtos;
		
	}
	
}
