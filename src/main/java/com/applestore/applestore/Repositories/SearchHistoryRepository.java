package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.SearchHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

	List<SearchHistory> findByCustomer(Customer customer);
	
	
}
