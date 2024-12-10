package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.*;
import com.applestore.applestore.Entities.*;
import com.applestore.applestore.Repositories.OrderItemRepository;
import com.applestore.applestore.Repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    
    
    
    
    
    public OrderDto convertEntityToDto(OrderItem orderItem){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderItem_id(orderItem.getOrder().getOrderId());
        orderDto.setProduct_id(orderItem.getProduct().getProductId());
        orderDto.setOrder_id(orderItem.getOrderItemId());
        orderDto.setOrder_date(formatLocalDateTime(orderItem.getOrder().getOrderDate()));
        orderDto.setCustomer_id(orderItem.getOrder().getCustomer().getCustomerId());
        orderDto.setNote(orderItem.getNote());
        orderDto.setStatus(orderItem.getOrderStatus());
        return orderDto;
    }

    public OrderItem convertDtoToEntity(OrderDto orderDto){
    	OrderItem orderItem = new OrderItem();
    	Product product = productService.getProductById(orderDto.getProduct_id());
    	Order order = orderRepository.getReferenceById(orderDto.getOrder_id());
    	orderItem.setProduct(product);
    	orderItem.setNote(orderDto.getNote());
    	orderItem.setOrderStatus(orderDto.getStatus());
    	orderItem.setOrder(order);
        return orderItem;
    }

    public List<OrderDto> getAllOrder(){
        List<OrderDto> listAllOrder = new ArrayList<>();
        for (OrderItem orderItem : orderItemRepository.findAll()){
            listAllOrder.add(convertEntityToDto(orderItem));
        }
        return listAllOrder;
    }

    // LAY RA THONG TIN CHI TIET CUA TAT CA CAC ORDER
    public List<detailOrderDto> getDetailOrder(){
        List<detailOrderDto> listDetailOrder = new ArrayList<>();
        for (OrderDto orderDto : getAllOrder()){
            CustomerDto customer = customerService.getCustomerById(orderDto.getCustomer_id());
            User user = userService.getUserById(customer.getUser_id());
            ProductDto product = productService.convertProductToDto(productService.getProductById(orderDto.getProduct_id()));

            detailOrderDto detailOrder = new detailOrderDto();
            detailOrder.setOrder_id(orderDto.getOrder_id());
            detailOrder.setOrder_date(orderDto.getOrder_date());
            detailOrder.setProduct_name(product.getName());
            detailOrder.setProduct_color(product.getColor());
            detailOrder.setCustomer_f_name(user.getF_name());
            detailOrder.setCustomer_l_name(user.getL_name());
            detailOrder.setPhone(customer.getPhone());
            detailOrder.setPrice(product.getPrice());
            detailOrder.setAddress_line(customer.getAddress_line());
            detailOrder.setCity(customer.getCity());
            detailOrder.setCountry(customer.getCountry());
            detailOrder.setOrderStatus(orderDto.getStatus());
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }

    // DANH SACH CAC ORDER DUOC DUYET HOAC CHUA
    public List<detailOrderDto> getStatusOrder(int status){
    	List<OrderDto> list = new ArrayList<>();
        for (OrderItem orderItem : orderItemRepository.findByOrderStatus(status)){
            list.add(convertEntityToDto(orderItem));
        }
        return getMoreDetailOrder(list);
    }

    public List<detailOrderDto> getCanceledOrder(int status){
        List<OrderDto> list = new ArrayList<>();
        for (OrderItem orderItem : orderItemRepository.findByOrderStatus(status)){
            list.add(convertEntityToDto(orderItem));
        }
        return getMoreDetailOrder(list);
    }

    // LAY THONG TIN CHI TIET VE CAC ORDER (SP, KHACH HANG,...)
    public List<detailOrderDto> getMoreDetailOrder(List<OrderDto> list){
        List<detailOrderDto> listDetailOrder = new ArrayList<>();
        for (OrderDto order : list){
            CustomerDto customer = customerService.getCustomerById(order.getCustomer_id());
            User user = userService.getUserById(customer.getUser_id());
            ProductDto product = productService.convertProductToDto(productService.getProductById(order.getProduct_id()));

            detailOrderDto detailOrder = new detailOrderDto();
            detailOrder.setOrder_id(order.getOrder_id());
            detailOrder.setProduct_id(product.getProduct_id());
            detailOrder.setOrder_date(order.getOrder_date());
            detailOrder.setProduct_name(product.getName());
            detailOrder.setProduct_color(product.getColor());
            detailOrder.setCustomer_f_name(user.getF_name());
            detailOrder.setCustomer_l_name(user.getL_name());
            detailOrder.setPhone(customer.getPhone());
            detailOrder.setPrice(product.getPrice());
            detailOrder.setAddress_line(customer.getAddress_line());
            detailOrder.setCity(customer.getCity());
            detailOrder.setNote(order.getNote());
            detailOrder.setCountry(customer.getCountry());

            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }
    
    
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return localDateTime.format(formatter);
    }
    
    //lấy thông tin đơn hàng bằng id khách hàng
    public List<OrderDto> getOrderByCustomerId_ASC(Customer customer){
        List<OrderDto> listOrder = new ArrayList<>();
        
        for (Order order : orderRepository.findByCustomer(customer)){
        	for (OrderItem orderItem : orderItemRepository.findByOrder(order) ){
	        	OrderDto orderDto = new OrderDto();
	        	
	        	orderDto.setOrder_id(orderItem.getOrderItemId());
	        	orderDto.setCustomer_id(orderItem.getOrder().getCustomer().getCustomerId());
	        	orderDto.setProduct_id(orderItem.getProduct().getProductId());
	        	orderDto.setOrder_date(formatLocalDateTime(orderItem.getOrder().getOrderDate()));
	        	orderDto.setStatus(orderItem.getOrderStatus());
	        	orderDto.setNote(orderItem.getNote());
	        	orderDto.setRatingStatus(orderItem.getRatingStatus());
	        	listOrder.add(orderDto);
        	}
        }
        
        return listOrder;
    }
    
//    public List<OrderDto> getOrderByCustomerId_DESC(int customer_id){
//        List<OrderDto> listOrder = new ArrayList<>();
//        for (Order order : orderRepository.findByCustomerId_DESC(customer_id)){
//        	OrderDto orderDto = new OrderDto();
//        	orderDto.setOrder_id(order.getOrder_id());
//        	orderDto.setCustomer_id(order.getCustomer_id());
//        	orderDto.setProduct_id(order.getProduct_id());
//        	orderDto.setOrder_date(order.getOrder_date());
//        	orderDto.setStatus(order.getStatus());
//        	orderDto.setNote(order.getNote());
//        	listOrder.add(orderDto);
//        }
//        
//        return listOrder;
//    }
    
    public List<detailOrderDto> getDetailOrderByCustomerId(Customer customer, String Time, String Status){
        List<detailOrderDto> listOrder = new ArrayList<>();
        
        
        	for (OrderDto orderDto : getOrderByCustomerId_ASC(customer)){
	            CustomerDto customerDto = customerService.getCustomerById(orderDto.getCustomer_id());
	            Product product = productService.getProductById(orderDto.getProduct_id());
	            detailOrderDto detailOrder = new detailOrderDto();
	            detailOrder.setProduct_id(product.getProductId());
	            detailOrder.setOrder_id(orderDto.getOrder_id());
	            detailOrder.setOrder_date(orderDto.getOrder_date());
	            detailOrder.setProduct_name(product.getName());
	            detailOrder.setProduct_color(product.getColor());
	            detailOrder.setPrice(String.valueOf(product.getPrice()));
	            detailOrder.setAddress_line(customerDto.getAddress_line() + ", " + customerDto.getCity() + ", " + customerDto.getCountry());
	            detailOrder.setImg(product.getImg());
	            detailOrder.setRatingStatus(orderDto.getRatingStatus());
	            if(orderDto.getStatus() == -1) {
	            	detailOrder.setStatus("Đơn hàng bị hủy");
	            }
	            if(orderDto.getStatus() == 0) {
	            	detailOrder.setStatus("Chờ xét duyệt");
	            }
	            if(orderDto.getStatus() == 1) {
	            	detailOrder.setStatus("Đã xét duyệt");
	            }
	            if(orderDto.getStatus() == 2) {
	            	detailOrder.setStatus("Đã giao hàng");
	            }
	            detailOrder.setNote(orderDto.getNote());
	            
	           

	            listOrder.add(detailOrder);
	        }
        
        
        if (Status != null && (Status.equals("Chờ xét duyệt") || Status.equals("Đã xét duyệt") || Status.equals("Đã giao hàng") || Status.equals("Đơn hàng bị hủy"))) {
            List<detailOrderDto> filteredList = new ArrayList<>();
            for (detailOrderDto detailOrder : listOrder) {
                if (detailOrder.getStatus().equals(Status)) {
                    filteredList.add(detailOrder);
                }
            }
            return filteredList;
        }
        
        return listOrder;
    }
    

    public void updateOrderStatus(int status, int orderItemId, String note){
        if (note.isEmpty()){
            OrderItem orderItem = orderItemRepository.getReferenceById(orderItemId);
            orderItem.setOrderStatus(status);
            orderItemRepository.save(orderItem);
        } else {
        	OrderItem orderItem = orderItemRepository.getReferenceById(orderItemId);
        	orderItem.setOrderStatus(status);
        	orderItem.setNote(note);
        	orderItemRepository.save(orderItem);
        }

    }
    
    public Order saveOrder(LocalDateTime currentDateTime, Customer customer) {
    	Order order = new Order();
    	order.setCustomer(customer);
    	order.setOrderDate(currentDateTime);
    	order.setAddressLine(customer.getAddressLine() +", "+customer.getCity()+", "+customer.getCountry());
    	return orderRepository.save(order);
    }
    
    public void saveOrderItem(Order order, Product product, int quantity, int order_status) {
    	OrderItem orderItem = new OrderItem();
    	orderItem.setOrder(order);
    	orderItem.setProduct(product);
    	orderItem.setQuantity(quantity);
    	orderItem.setOrderStatus(order_status);
    	orderItem.setRatingStatus(0);
    	orderItemRepository.save(orderItem);
    }
    
    public OrderItem getOrderItemById(int id) {
    	
    	return orderItemRepository.getReferenceById(id);
    }
    
    public void update(int id) {
    	
    	OrderItem orderItem = orderItemRepository.getReferenceById(id);
		orderItem.setRatingStatus(1);
		orderItemRepository.save(orderItem);
    }
}
