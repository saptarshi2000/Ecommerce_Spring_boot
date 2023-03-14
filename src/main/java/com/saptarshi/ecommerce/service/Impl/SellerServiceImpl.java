package com.saptarshi.ecommerce.service.Impl;

import com.saptarshi.ecommerce.dto.*;
import com.saptarshi.ecommerce.exception.CustomException;
import com.saptarshi.ecommerce.model.Product;
import com.saptarshi.ecommerce.model.Seller;
import com.saptarshi.ecommerce.model.Tag;
import com.saptarshi.ecommerce.repository.ProductRepository;
import com.saptarshi.ecommerce.repository.SellerRepository;
import com.saptarshi.ecommerce.repository.TagRepository;
import com.saptarshi.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    private final TagRepository tagRepository;

    @Override
    public RestResponse regAsSeller(SellerRegistrationRequest request) {
        if(sellerRepository.findByUserEmail(request.getUserEmail()).isPresent()){
            throw new CustomException("seller already registered");
        }
        Random random = new Random();
        long randomLong = random.nextInt(900000000) + 1000000000L;
        Seller seller = Seller.builder()
                .sellerName(request.getSellerName())
                .userEmail(request.getUserEmail())
                .registration(randomLong)
                .build();
        sellerRepository.save(seller);
        return new RestResponse("registered as a seller", HttpStatus.valueOf(201));
    }

    @Override
    public RestResponse addProduct(ProductAddRequest request) {
        Seller seller = sellerRepository.findByUserEmail(request.getSellerEmail()).orElseThrow();
        List<Tag> tags = new ArrayList<>();
        for(Tag tag: request.getTags()){
            Optional<Tag> t = tagRepository.findByTagName(tag.getTagName());
            if(t.isPresent()){
                tags.add(t.get());
            }else{
                tags.add(tag);
            }
        }
        System.out.println(request.getProductPrice());
        Product product = Product.builder()
                .brand(request.getBrand())
                .productName(request.getProductName())
                .productPrice(request.getProductPrice())
                .color(request.getColor())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .stock(request.getStock())
                .active(true)
                .discount(request.getDiscount())
                .tags(tags)
                .seller(seller)
                .build();
        productRepository.save(product);
        return new RestResponse("product added to store",HttpStatus.CREATED);
    }

    @Override
    public RestResponse updateProduct(ProductUpdateRequest request) {

        Product product = productRepository.findById(request.getProduct_id()).orElseThrow();

        product.setProductName(request.getProductName());
        product.setProductPrice(request.getProductPrice());
        product.setColor(request.getColor());
        product.setStock(request.getStock());
        product.setDiscount(request.getDiscount());

        productRepository.save(product);
        return new RestResponse("product added to store",HttpStatus.CREATED);
    }

    @Override
    public RestResponse deleteProduct(long productId) {
        productRepository.deleteById(productId);
        return new RestResponse("product deleted",HttpStatus.OK);
    }

    @Override
    public ListRestResponse getAllProducts(String sellerEmail) {
        List<Product> products = sellerRepository.findByUserEmail(sellerEmail).orElseThrow(() ->
                new CustomException("seller not found")).getProducts();
        return new ListRestResponse("products",HttpStatus.OK,products);
    }
}
