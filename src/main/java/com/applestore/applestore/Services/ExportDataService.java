package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.DTOs.ProductReviewDto;
import com.applestore.applestore.DTOs.SearchHistoryDto;
import com.applestore.applestore.DTOs.ShoppingCartDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Product;
import com.opencsv.CSVWriter;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportDataService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductReviewService productReviewService;
	
	@Autowired
	private SearchHistoryService searchHistoryService;
	
	
	public void checkAndDeleteOldFile(String filePath) {
	    File file = new File(filePath);
	    if (file.exists()) {
	        file.delete();
	    }
	}
	
	public void exportAllProductsToCSV() throws IOException {
		checkAndDeleteOldFile("D:/data_exports/all_products.csv");
		List<Product> products = productService.listALlProduct();
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:/data_exports/all_products.csv"))) {
            writer.writeNext(new String[]{"name", "color","storage_capacity", "price"});
            for (Product product : products) {
                writer.writeNext(new String[]{product.getName(), product.getColor(), product.getStorageCapacity(), String.valueOf(product.getPrice())});
            }
        }
    }
	
	public void exportShoppingCartToCSV(Customer customer) throws IOException {
		checkAndDeleteOldFile("D:/data_exports/cart_products.csv");
		List<ShoppingCartDto> shoppingCartDtos = shoppingCartService.convertToDto(customer);
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:/data_exports/cart_products.csv"))) {
            writer.writeNext(new String[]{"name", "color","storage_capacity", "price"});
            for (ShoppingCartDto shoppingCartDto : shoppingCartDtos) {
                writer.writeNext(new String[]{shoppingCartDto.getProductName(), shoppingCartDto.getColor(), shoppingCartDto.getStorage_capacity(), shoppingCartDto.getPrice()});
            }
        }
    }
	
	public void exportProductReviewToCSV(Customer customer) throws IOException {
		checkAndDeleteOldFile("D:/data_exports/ProductReview.csv");
		List<ProductReviewDto> productReviewDtos = productReviewService.convertToDto(customer);
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:/data_exports/ProductReview.csv"))) {
            writer.writeNext(new String[]{"rating","name", "color","storage_capacity", "price", "evaluation_time"});
            for (ProductReviewDto productReviewDto : productReviewDtos) {
                writer.writeNext(new String[]{productReviewDto.getRating(), productReviewDto.getProductName(), productReviewDto.getColor(), productReviewDto.getStorage_capacity(), productReviewDto.getPrice(), productReviewDto.getEvaluation_time()});
            }
        }
    }
	

}
