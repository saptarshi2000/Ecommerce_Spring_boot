package com.saptarshi.ecommerce.service;

import com.saptarshi.ecommerce.dto.*;
import com.saptarshi.ecommerce.model.Product;

public interface SellerService {

    RestResponse regAsSeller(SellerRegistrationRequest request);

    RestResponse addProduct(ProductAddRequest request);

    RestResponse updateProduct(ProductUpdateRequest request);

    RestResponse deleteProduct(long productId);

    ListRestResponse getAllProducts(String sellerEmail);

}
