package com.saptarshi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegistrationRequest {

    private String sellerName;

    private String userEmail;
}
