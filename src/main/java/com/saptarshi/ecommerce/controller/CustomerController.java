package com.saptarshi.ecommerce.controller;

import com.saptarshi.ecommerce.dto.ListRestResponse;
import com.saptarshi.ecommerce.dto.RestResponse;
import com.saptarshi.ecommerce.model.Address;
import com.saptarshi.ecommerce.model.Customer;
import com.saptarshi.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/reg")
    public ResponseEntity<RestResponse> regAsCustomer(@RequestBody Customer customer){
        RestResponse response = customerService.regAsCustomer(customer);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/search")
    public ResponseEntity<ListRestResponse> searchProducts(@RequestParam String query,@RequestParam int min,
                                                           @RequestParam int max,@RequestParam String color){
        ListRestResponse listRestResponse = customerService.findProducts(query, min, max, color);

        return new ResponseEntity<>(listRestResponse, HttpStatus.OK);
    }


    @PostMapping("/add_to_cart")
    public ResponseEntity<RestResponse> addToCart(@RequestParam String email,@RequestParam long id){
        RestResponse response = customerService.addToCart(email,id);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/decrease_quantity")
    public ResponseEntity<RestResponse> decreaseQuantity(@RequestParam long cartItemId){
        RestResponse response = customerService.decreaseQuantity(cartItemId);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @DeleteMapping("/delete_from_cart")
    public ResponseEntity<RestResponse> deleteFromCart(@RequestParam long cartItemId){
        RestResponse response = customerService.removeFromCart(cartItemId);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PostMapping("/add_address")
    public ResponseEntity<RestResponse> addAddress(@RequestParam String email,@RequestBody Address address){
        RestResponse response = customerService.addAddress(email,address);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/get_all_address")
    public ResponseEntity<ListRestResponse> getAllResponse(@RequestParam String email){
        ListRestResponse response = customerService.getAllAddress(email);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @DeleteMapping("/delete_address")
    public ResponseEntity<RestResponse> deleteAddress(@RequestParam long id){
        RestResponse response = customerService.deleteAddress(id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PostMapping("/order")
    public ResponseEntity<RestResponse> order(@RequestParam long cartId,@RequestParam long addressId){
        RestResponse response = customerService.order(cartId,addressId);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/get_all_orders")
    public ResponseEntity<ListRestResponse> getAllOrder(@RequestParam String email){
        ListRestResponse response = customerService.getAllOrder(email);
        return new ResponseEntity<>(response,response.getStatus());
    }

}
