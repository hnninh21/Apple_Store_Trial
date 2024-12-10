package com.applestore.applestore.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private int user_id, customer_id;
    private String name, address_line, country, city, phone;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "user_id=" + user_id +
                ", customer_id=" + customer_id +
                ", address_line='" + address_line + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
