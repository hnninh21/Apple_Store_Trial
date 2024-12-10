package com.applestore.applestore.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applestore.applestore.DTOs.ColorDto;
import com.applestore.applestore.DTOs.ShoppingCartDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Entities.ShoppingCart;
import com.applestore.applestore.Repositories.ShoppingCartRepository;


@Service
public class ShoppingCartService {
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CustomerService customerService;
	
	public void addToCart(Product product, Customer customer){
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setProduct(product);
		shoppingCart.setCustomer(customer);
		shoppingCart.setQuantity(1);
		shoppingCartRepository.save(shoppingCart);
    }
	
	public List<ShoppingCart> cartItemsByCustomer(Customer customer){
		List<ShoppingCart> cartItems = new ArrayList<ShoppingCart>();
		cartItems = shoppingCartRepository.findByCustomer(customer.getCustomerId());
		
		return cartItems;
	}
	
	public List<ShoppingCart> listCartItemsById(List<Integer> selectedItems){
		List<ShoppingCart> cartItems = new ArrayList<ShoppingCart>();
		
		for (int itemId : selectedItems){
			ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(itemId);         
			cartItems.add(shoppingCart);
        }	
		return cartItems;
	}
	
	public ShoppingCart cartItemsById(int cartItemID){
		ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(cartItemID);   	
		return shoppingCart;
	}
	
	public void deleteCartItem(int id){	
		shoppingCartRepository.deleteById(id);
    }
	
	public void decreaseCartItem(int id){	
		ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(id);
		shoppingCart.setQuantity(shoppingCart.getQuantity()-1);
		shoppingCartRepository.save(shoppingCart);
    }
	
	public void addCartItem(int id){	
		ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(id);
		shoppingCart.setQuantity(shoppingCart.getQuantity()+1);
		shoppingCartRepository.save(shoppingCart);
    }
	
	public List<ShoppingCartDto> convertToDto(Customer customer){

		List<ShoppingCartDto> shoppingCartDtos = new ArrayList<>();
		List<ShoppingCart> shoppingCarts =  cartItemsByCustomer(customer);
		for (ShoppingCart shoppingCart : shoppingCarts){
			ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
			shoppingCartDto.setProductName(shoppingCart.getProduct().getName());
			shoppingCartDto.setColor(shoppingCart.getProduct().getColor());
			shoppingCartDto.setStorage_capacity(shoppingCart.getProduct().getStorageCapacity());
			shoppingCartDto.setPrice(String.valueOf(shoppingCart.getProduct().getPrice()));
			shoppingCartDtos.add(shoppingCartDto);
        }
		
		return shoppingCartDtos;
		
	}
	
	public boolean isProductInCart(Product product, Customer customer) {
	    // Lấy danh sách các mục trong giỏ hàng của khách hàng
	    List<ShoppingCart> cartItems = shoppingCartRepository.findByCustomer(customer.getCustomerId());

	    // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
	    for (ShoppingCart cartItem : cartItems) {
	        if (cartItem.getProduct().getProductId() == product.getProductId()) {
	            return true; // Sản phẩm đã tồn tại
	        }
	    }
	    return false; // Sản phẩm chưa tồn tại
	}

	
}
