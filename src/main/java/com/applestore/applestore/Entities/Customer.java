package com.applestore.applestore.Entities;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.sql.ast.tree.expression.Collation;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @Column(name = "address_line", columnDefinition = "nvarchar(200)")
    private String addressLine;
    
    @Column(name = "city", columnDefinition = "nvarchar(50)")
    private String City;
    
    @Column(name = "country", columnDefinition = "nvarchar(50)")
    private String Country;
    
    @Column(name = "phone")
    private String phone;

    
    public Customer() {
    }


    public Customer(int customerId, User user, String addressLine, String city, String country, String phone) {
		super();
		this.customerId = customerId;
		this.user = user;
		this.addressLine = addressLine;
		this.City = city;
		this.Country = country;
		this.phone = phone;
	}



	public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


	public String getCity() {
		return City;
	}


	public void setCity(String city) {
		City = city;
	}


	public String getCountry() {
		return Country;
	}


	public void setCountry(String country) {
		Country = country;
	}
    
    
}
