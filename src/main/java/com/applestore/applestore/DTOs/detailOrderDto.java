package com.applestore.applestore.DTOs;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class detailOrderDto {
    private int order_id, product_id, orderStatus, ratingStatus;
    private String price, product_name, product_color, customer_l_name, customer_f_name, address_line, city, country, order_date, phone, img, status, note;

    public int getIntPrice() {
        String stringPrice = price.replace(".", "").replace(" ", "").replace("₫", "").replace(",", "");
        int intPrice = Integer.parseInt(stringPrice);
        return intPrice;
    }
}
