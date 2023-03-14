package com.saptarshi.ecommerce.dto;

import com.saptarshi.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {

    private String message;

    private HttpStatus status;

    List<Product> products;

}
