package com.applestore.applestore.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import com.applestore.applestore.Entities.*;
import com.applestore.applestore.Repositories.OrderItemRepository;
import com.applestore.applestore.Services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.applestore.applestore.DTOs.ColorDto;
import com.applestore.applestore.DTOs.CustomerDto;
import com.applestore.applestore.DTOs.OrderDto;
import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.DTOs.ReceiveProductDto;
import com.applestore.applestore.DTOs.detailOrderDto;
import com.applestore.applestore.Security.CustomUserDetails;
import com.applestore.applestore.Services.CustomerService;
import com.applestore.applestore.Services.ExportDataService;
import com.applestore.applestore.Services.OrderService;
import com.applestore.applestore.Services.ProductService;
import com.applestore.applestore.Services.ShoppingCartService;
import com.applestore.applestore.Services.ProductReviewService;
import com.applestore.applestore.Services.SearchHistoryService;
import java.io.*;


import org.springframework.security.core.Authentication;

@Controller

@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
	
	@Autowired
    private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService ;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private ShoppingCartService shoppingCartService;
    
    @Autowired 
    private ProductReviewService productReviewService ;
    
    @Autowired 
    private SearchHistoryService searchHistoryService;
    
    @Autowired 
    private ExportDataService exportDataService;

    public User curUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String gmail = "";
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            gmail =  oauthUser.getAttribute("email");
        } else if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            gmail =  userDetails.getGmail();
        }
        return userService.findByGmail(gmail);
    }
    
    
    @GetMapping("/")
    public String goiysanpham(Model model) {
        User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        
        if(customerDto == null) {
        	List<ProductDto> listAllProduct = productService.listALlProductDTo();
        	if(listAllProduct.size() > 20){
        		listAllProduct = listAllProduct.subList(0, 5);
        	}
        	model.addAttribute("listAllProduct", listAllProduct);
        	return "/Fragments/user/index";
        }
        
        Customer customer = customerService.convertCustomerDtoToCustomer(customerDto);
        try {
            // Xuất các file CSV
            exportDataService.exportAllProductsToCSV();
            exportDataService.exportShoppingCartToCSV(customer);
            exportDataService.exportProductReviewToCSV(customer);

            // Gọi file Python
            ProcessBuilder processBuilder = new ProcessBuilder("python", "D:/JAVA/ProductRecommendation/ProductRecommendation.py");
            Process process = processBuilder.start();

            // Đọc dữ liệu từ luồng đầu ra của Python
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // Chuyển đổi chuỗi JSON thành danh sách đối tượng Java
            ObjectMapper objectMapper = new ObjectMapper();
            List<ReceiveProductDto> recommendations = objectMapper.readValue(output.toString(), 
                    new TypeReference<List<ReceiveProductDto>>() {});

            List<ProductDto> listAllProduct = productService.recommendedProducts(recommendations);
                                 
            model.addAttribute("listAllProduct", listAllProduct);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Error executing Python script.");
        }

        return "/Fragments/user/index";
    }

    
//    @GetMapping("/")
//    public String index(Model model){
//        List<ProductDto> listAllProduct = productService.listALlProductDTo();
//        if(listAllProduct.size() > 20){
//            listAllProduct = listAllProduct.subList(0, 5);
//        }
//        model.addAttribute("listAllProduct", listAllProduct);
//    	return "/Fragments/user/index";
//    }
    
	
    @GetMapping("/customer_info")
    public String CustomerInfo(Model model,Authentication authentication) {
    	
        User user = curUser();
    	CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		List<CustomerDto> CustomerDtos = customerService.list_CustomerDto();
		List<String> ds_SDT = new ArrayList<String>();
		String tmp = null;
		if(customerDto != null) {
			tmp = customerDto.getPhone();
		}
		
		for(CustomerDto ctmDto : CustomerDtos) {
			
			if(ctmDto.getPhone().equals(tmp)) {
				
			}else {
				ds_SDT.add(ctmDto.getPhone());
			}		
		}
		
		model.addAttribute("ds_SDT", ds_SDT);
		model.addAttribute("customerDto", customerDto);
    	model.addAttribute("user", user);
    	
        return "/Fragments/user/customer_info";
    }
    
    @PostMapping("/save")
    public String save(
            @RequestParam("address_line") String address_line,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("phone") String phone,
            Authentication authentication,
            Model model
    ){  
    	
    	if (phone.length() != 10) {
            model.addAttribute("error", "Số điện thoại phải có đúng 10 ký tự !");
            model.addAttribute("address_line", address_line);
            model.addAttribute("country", country);
            model.addAttribute("city", city);
            model.addAttribute("phone", phone);
            return "/Fragments/user/customer_info";
        }

    	List<CustomerDto> customerDtos = customerService.list_CustomerDto();
        for (CustomerDto ctmDto : customerDtos) {
            if (ctmDto.getPhone().equals(phone)) {
                model.addAttribute("error", "Số điện thoại đã tồn tại !");
                model.addAttribute("address_line", address_line);
                model.addAttribute("country", country);
                model.addAttribute("city", city);
                model.addAttribute("phone", phone);
                return "/Fragments/user/customer_info";
            }
        }
    	
        User user = curUser();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUser_id(user.getUser_id());
        customerDto.setAddress_line(address_line);
        customerDto.setCountry(country);
        customerDto.setCity(city);
        customerDto.setPhone(phone);
    
        Customer customer = customerService.convertCustomerDtoToCustomer(customerDto);
        customerService.saveCustomer(customer);


        
        return "redirect:/user/customer_info";
    }

    
    
    @PostMapping("/update")
    public String update(
            @RequestParam("address_line") String address_line,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("phone") String phone,
            Authentication authentication,
            Model model
    ){  

        User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer = customerService.getCustomerById1(customerDto.getCustomer_id());
        customer.setAddressLine(address_line);
        customer.setCountry(country);
        customer.setCity(city);
        customer.setPhone(phone);
        customerService.saveCustomer(customer);
        
        
   
        return "redirect:/user/customer_info";
    }
    
	
    
    @GetMapping("/products")
    public String products(Model model, @Param("search") String search, @Param("comboBox") String price, @Param("color") String color){

        List<ProductDto> listAllProduct = new ArrayList<>();
        List<String> listColor = productService.getAllColor();
        System.out.println("Color: "+color);
        if (search == null && price == null && color == null || Objects.equals(color, "")) {
            System.out.println("Normal list: ");
            listAllProduct = productService.listALlProductDTo();
        }
        else if (search != null){
            System.out.println("Search result: ");
            listAllProduct = productService.findProductByName(search);
        }
        else {
            if (price != null){
                if (price.equals("asc")){
                    System.out.println("List ordered asc: ");
                    listAllProduct = productService.getAllOrderByPrice(true);
                } else if (price.equals("desc")){
                    System.out.println("List ordered desc: ");
                    listAllProduct = productService.getAllOrderByPrice(false);
                }
            } else {
                listAllProduct = productService.getProductByColor(color);
            }
        }
        model.addAttribute("listAllProduct", listAllProduct);
        model.addAttribute("listColor", listColor);
        System.out.println("SelectedItem: " + price);
        System.out.println("SelectedColor: " + color);

        return "/Fragments/user/view-all-product";
    }

    @GetMapping("/search")
    public String searchProd(Model model, @Param("search") String search, @Param("comboBox") String price, @Param("color") String color){
    	User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);
    	
        List<ProductDto> resultSearchList = productService.listALlProductDTo();
        List<String> listColor = productService.getAllColor();
        if (search != null && !search.isEmpty()){
        	searchHistoryService.save(customer, search);
            System.out.println("Search result: ");
            resultSearchList = resultSearchList.stream().filter((x)-> x.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        }

        if (color != null && !color.isEmpty()) {
            resultSearchList = resultSearchList.stream()
                    .filter(product -> product.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
        }

        if (price != null && (price.equalsIgnoreCase("asc") || price.equalsIgnoreCase("desc"))) {
            if (price.equalsIgnoreCase("asc")) {
                resultSearchList.sort(Comparator.comparingInt(ProductDto::getIntPrice));
            } else if (price.equalsIgnoreCase("desc")) {
                resultSearchList.sort(Comparator.comparingInt(ProductDto::getIntPrice).reversed());
            }
        }
        model.addAttribute("resultSearchList", resultSearchList);
        model.addAttribute("listColor", listColor);

        model.addAttribute("searchKey", search);
        return "/Fragments/user/search-prod";

    }

    @GetMapping("/product_detail")
    public String product_detail(@RequestParam("product_id") int productId,
                                 @RequestParam(value = "color", required = false) String color,
                                 Model model) {
        User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());

        
        Product product = productService.getProductById(productId);
        ProductDto productDto = productService.convertProductToDto(product);

        
        List<ColorDto> colorDtos = productService.getColorDtosForProduct(productId);

        
        if (color != null && !color.isEmpty()) {
            productDto = productService.getProductByColor(productId, color);
            
        }

        model.addAttribute("productDto", productDto);
        model.addAttribute("customerDto", customerDto);
        model.addAttribute("colorDtos", colorDtos);
        //model.addAttribute("selectedColor", color);

        return "/Fragments/user/product_detail";
    }
    
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("product_id") int productId, Model model) {
        User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
       
        if (customerDto == null) {
            model.addAttribute("error", "Vui lòng hoàn thành thông tin khách hàng trước khi thêm sản phẩm vào giỏ hàng.");
            return "redirect:/user/customer_info";
        }

        Customer customer = customerService.convertCustomerDtoToCustomer(customerDto);
        Product product = productService.getProductById(productId);
        boolean productExistsInCart = shoppingCartService.isProductInCart(product, customer);

        if (productExistsInCart) {
            model.addAttribute("message", "Sản phẩm đã tồn tại trong giỏ hàng.");
        } else {           
            shoppingCartService.addToCart(product, customer);
            model.addAttribute("message", "Thêm sản phẩm thành công!");
        }        
        ProductDto productDto = productService.convertProductToDto(product);      
        List<ColorDto> colorDtos = productService.getColorDtosForProduct(productId);

        model.addAttribute("productDto", productDto);
        model.addAttribute("customerDto", customerDto);
        model.addAttribute("colorDtos", colorDtos);

        return "/Fragments/user/product_detail";
    }

    
       
    @PostMapping("/checkout")
    public String checkout(
    		@RequestParam("product_id") int product_id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes
    ) {	

        User user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);
		LocalDateTime currentDateTime = LocalDateTime.now();
        
		Order order = orderService.saveOrder(currentDateTime, customer);
		
		Product product = productService.getProductById(product_id);
		
        orderService.saveOrderItem(order, product, 1, 0);
        product.setStock(String.valueOf(Integer.parseInt(product.getStock())-1));
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công");
		
        ProductDto productDto = productService.convertProductToDto(product);   
             
        List<ColorDto> colorDtos = productService.getColorDtosForProduct(product_id);
        
   
        model.addAttribute("productDto", productDto);
        model.addAttribute("customerDto", customerDto);
        model.addAttribute("colorDtos", colorDtos);
        model.addAttribute("message", "Đặt hàng thành công!");   
        
        return "/Fragments/user/product_detail";
		
		
		
    }

    @PostMapping("/purchase-detail-info")
    public String purchaseDetailInfo(
            CustomerDto customerDto
    ){
        System.out.println(customerDto);
        return "redirect:/user/";
    }
    
    
    @GetMapping("/purchase_history")
    public String purchaseHistory(Model model, @Param("Time") String Time, @Param("Status") String Status ){

        User user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);
		
		if(customerDto != null) {
			List<detailOrderDto> listOrder = new ArrayList<>();
			listOrder = orderService.getDetailOrderByCustomerId(customer,Time, Status );
					
			
	        model.addAttribute("listDetailOrder",listOrder );
	        
	        return "/Fragments/user/purchase_history";
		}
		
		return "/Fragments/user/purchase_history";
    }
    
    @PostMapping("/purchase_history/rating")    
    public String rating(	@RequestParam("orderItem_id") int orderItem_id,
    						@RequestParam("productId") int productId,
            				@RequestParam("rating") int rating,
            				@RequestParam("review_text") String review_text,
            				RedirectAttributes redirectAttributes){

        User user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);		
		Product product = productService.getProductById(productId);		
		orderService.update(orderItem_id);				
		LocalDateTime currentDateTime = LocalDateTime.now();		
		productReviewService.save(customer, product, rating, review_text, currentDateTime);
		
		redirectAttributes.addFlashAttribute("message", "Đánh giá thành công");	     
        return "redirect:/user/purchase_history";
    }
    
    
    
    
    @GetMapping("/shopping-cart")
    public String shoppingCart(Model model){
    	
    	User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);
   	
        List<ShoppingCart> cartItems = shoppingCartService.cartItemsByCustomer(customer);
        
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("customerDto", customerDto);
		return "/Fragments/user/shopping-cart";
    }

    @PostMapping("/shopping-cart/decrease")
    public String decreaseCartItem(@RequestParam("cartItemId") int cartItemId, RedirectAttributes redirectAttributes) {
    	ShoppingCart shoppingCart = shoppingCartService.cartItemsById(cartItemId);
    	if(shoppingCart.getQuantity() == 1) {
    		redirectAttributes.addFlashAttribute("message", "Số lượng đã tối Thiểu!");        
            return "redirect:/user/shopping-cart";
		}
    	
        shoppingCartService.decreaseCartItem(cartItemId);             
        return "redirect:/user/shopping-cart";
    }
    
    @PostMapping("/shopping-cart/add")
    public String addCartItem(@RequestParam("cartItemId") int cartItemId, RedirectAttributes redirectAttributes) {
    	ShoppingCart shoppingCart = shoppingCartService.cartItemsById(cartItemId);
    	if(shoppingCart.getQuantity() == Integer.parseInt(shoppingCart.getProduct().getStock())) {
    		redirectAttributes.addFlashAttribute("message", "Số lượng đã tối đa!");        
            return "redirect:/user/shopping-cart";
		}   
    	
        shoppingCartService.addCartItem(cartItemId);             
        return "redirect:/user/shopping-cart";
    }
    
    @PostMapping("/shopping-cart/delete")
    public String deleteCartItem(@RequestParam("cartItemId") int cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
        return "redirect:/user/shopping-cart"; 
    }
    
    
    @PostMapping("/shopping-cart/checkout")
    public String checkout(@RequestParam("selectedItems") List<Integer> selectedItems, Model model) {
    	User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer =  customerService.convertCustomerDtoToCustomer(customerDto);
        
        List<ShoppingCart> cartItems = shoppingCartService.listCartItemsById(selectedItems);
        
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("customerDto", customerDto);
        
        return "Fragments/user/checkout";
    }
    
    @PostMapping("/shopping-cart/checkout/save")
    public String checkoutSave(@RequestParam("cartItemIDs") List<Integer> cartItemIDs,RedirectAttributes redirectAttributes) {
        
        User user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer = customerService.convertCustomerDtoToCustomer(customerDto);
        LocalDateTime currentDateTime = LocalDateTime.now();
        

        Order order = orderService.saveOrder(currentDateTime, customer);

        for (int cartItemID : cartItemIDs) {
            ShoppingCart shoppingCart = shoppingCartService.cartItemsById(cartItemID);
            Product product = shoppingCart.getProduct();
            orderService.saveOrderItem(order, product, shoppingCart.getQuantity(), 0);
            product.setStock(String.valueOf(Integer.parseInt(product.getStock())-1));
            productService.saveProduct(product);
            shoppingCartService.deleteCartItem(cartItemID);
            
            
        }
               
        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công");
     
        return "redirect:/user/shopping-cart";
    }


    
    
    @GetMapping("/change-password" )
    public String changePasswordPage(Model model){
        User user = curUser();

        model.addAttribute("username", user.getUsername());
        return "Fragments/user/change_password";
    }
    @PostMapping("/change-password" )
    public String changePassword(@RequestParam("username") String username,
                                 @RequestParam("newPw") String newPw,
                                 @RequestParam("oldPw") String oldPw){
        System.out.println("taiKhoan: "+username);
        System.out.println("matKhauCu: "+oldPw);
        System.out.println("matKhauMoi: "+newPw);
        User user = userService.findByUsername(username);
        System.out.println("User: "+user);
        if (passwordEncoder.matches(oldPw, user.getPassword())) {
            userService.changePassword(username, newPw);
            return "redirect:/user/change-password?success";
        } else {
            return "redirect:/user/change-password?fail"; // Trả về lỗi nếu mật khẩu hiện tại không khớp
        }
    }
}
