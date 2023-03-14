package com.saptarshi.ecommerce.service;

import com.saptarshi.ecommerce.dto.ListRestResponse;
import com.saptarshi.ecommerce.dto.RestResponse;
import com.saptarshi.ecommerce.model.Address;
import com.saptarshi.ecommerce.model.Customer;

public interface CustomerService {

    RestResponse regAsCustomer(Customer customer);

    RestResponse addAddress(String email,Address address);

    RestResponse deleteAddress(long addressId);

    ListRestResponse getAllAddress(String email);

    ListRestResponse findProducts(String query, int minValue, int maxValue,String color);

    RestResponse addToCart(String email,long productId);

    RestResponse increaseQuantity(String email,long cartItemId);

    RestResponse decreaseQuantity(long cartItemId);

    RestResponse removeFromCart(long cartItemId);

    RestResponse order(long cartId,long addressId);

    RestResponse cancelOrder(long orderId);
    ListRestResponse getAllOrder(String email);

}
