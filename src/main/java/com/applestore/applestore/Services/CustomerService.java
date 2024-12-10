package com.applestore.applestore.Services;


import com.applestore.applestore.DTOs.CustomerDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.User;

import java.util.ArrayList;

import java.util.List;


import com.applestore.applestore.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private UserService userService;
    
    public CustomerDto getCustomerById(int id){
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.getReferenceById(id);
        customerDto.setUser_id(customer.getUser().getUser_id());
        customerDto.setCustomer_id(customer.getCustomerId());
        customerDto.setAddress_line(customer.getAddressLine());
        customerDto.setPhone(customer.getPhone());
        customerDto.setCity(customer.getCity());
        customerDto.setCountry(customer.getCountry());
        return customerDto;
    }
    
    public Customer getCustomerById1(int id){
        
        Customer customer = customerRepository.getReferenceById(id);
        return customer;
    }
    
    public CustomerDto getCustomerByuserId(int user_id){
        List<Customer> customers = customerRepository.findAll();
        		
        for (Customer customer : customers) {
            if (customer.getUser().getUser_id() == user_id) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setName(customer.getUser().getF_name() +" "+ customer.getUser().getL_name());
                customerDto.setUser_id(customer.getUser().getUser_id());
                customerDto.setCustomer_id(customer.getCustomerId());
                customerDto.setAddress_line(customer.getAddressLine());
                customerDto.setPhone(customer.getPhone());
                customerDto.setCity(customer.getCity());
                customerDto.setCountry(customer.getCountry());
                return customerDto;
            }
        }
        return null; // Trả về null nếu không tìm thấy khách hàng với user_id tương ứng
    }
    
    public Customer convertCustomerDtoToCustomer(CustomerDto customerDto){
    	Customer customer = new Customer();
    	User user = userService.getUserById(customerDto.getUser_id());
    	customer.setCustomerId(customerDto.getCustomer_id());
        customer.setUser(user);
        customer.setAddressLine(customerDto.getAddress_line());
        customer.setCity(customerDto.getCity());
        customer.setCountry(customerDto.getCountry());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }
    
    public void saveCustomer(Customer customer){

    	customerRepository.save(customer);
    }
    
    public List<CustomerDto> list_CustomerDto(){
    	List<Customer> customers = customerRepository.findAll();
    	List<CustomerDto> CustomerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setUser_id(customer.getUser().getUser_id());
            customerDto.setCustomer_id(customer.getCustomerId());
            customerDto.setAddress_line(customer.getAddressLine());
            customerDto.setPhone(customer.getPhone());
            customerDto.setCity(customer.getCity());
            customerDto.setCountry(customer.getCountry());
            CustomerDtos.add(customerDto);
        }
       
        return CustomerDtos;
    }

}
