package com.applestore.applestore.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private int orderItem_id, order_id, product_id, customer_id, status, ratingStatus;
    private String order_date, note;
}
