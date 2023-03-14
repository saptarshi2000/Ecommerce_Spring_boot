package com.saptarshi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {

    private long product_id;
    private String productName;

    private float productPrice;

    private float discount;

    private int stock;

    private String color;

    private String sellerEmail;
}
