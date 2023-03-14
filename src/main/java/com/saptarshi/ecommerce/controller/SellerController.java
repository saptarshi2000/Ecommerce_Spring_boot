package com.saptarshi.ecommerce.controller;

import com.saptarshi.ecommerce.dto.ListRestResponse;
import com.saptarshi.ecommerce.dto.ProductAddRequest;
import com.saptarshi.ecommerce.dto.RestResponse;
import com.saptarshi.ecommerce.dto.SellerRegistrationRequest;
import com.saptarshi.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/reg")
    public ResponseEntity<RestResponse> reqAsSeller(@RequestBody SellerRegistrationRequest request){
//        System.out.println(request.getSellerName());
        RestResponse response = sellerService.regAsSeller(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<RestResponse> addProduct(@RequestBody ProductAddRequest request){
        RestResponse response = sellerService.addProduct(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<RestResponse> updateProduct(@RequestBody ProductAddRequest request){
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RestResponse> deleteProduct(@RequestParam(name = "product_id") long productId){
        return null;
    }

    @GetMapping("/get_all_products")
    public ResponseEntity<ListRestResponse> getAllProduct(@RequestParam(name = "seller_email") String sellerEmail){
        return new ResponseEntity<>(sellerService.getAllProducts(sellerEmail),HttpStatus.OK);
    }

}
