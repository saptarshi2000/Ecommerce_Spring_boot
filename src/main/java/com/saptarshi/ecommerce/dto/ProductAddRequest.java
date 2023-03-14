package com.saptarshi.ecommerce.dto;

import com.saptarshi.ecommerce.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAddRequest {

    private String brand;
    private String productName;

    private float productPrice;

    private float discount;

    private int stock;

    private String color;

    private String sellerEmail;

    private List<Tag> tags;
}
