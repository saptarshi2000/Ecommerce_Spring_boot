package com.saptarshi.ecommerce.controller;

import com.saptarshi.ecommerce.model.*;
import com.saptarshi.ecommerce.repository.*;
import com.saptarshi.ecommerce.service.Impl.DataTransferService;
import com.saptarshi.ecommerce.service.Impl.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final ProductRepository productRepository;

    private final SellerRepository sellerRepository;

    private final TagRepository tagRepository;

    private final ElasticTagRepository elasticTagRepository;

    private final ElasticProductRepository elasticProductRepository;

    private final ElasticSearchService elasticSearchService;

    private final CartRepository cartRepository;

    private final DataTransferService dataTransferService;

    @GetMapping("/t1")
    public Object test() {
        Seller seller1 = Seller.builder()
                .sellerName("iseller")
                .registration(111)
                .userEmail("email")
                .build();
        sellerRepository.save(seller1);
        Tag t1 = tagRepository.findById(39l).get();
        Tag t2 = Tag.builder()
                .tagName("tag44")
                .build();

//        tagRepository.save(t1);
//        tagRepository.save(t2);

        Seller s = sellerRepository.findById((long) 1).get();
        List<Tag> tags = new ArrayList<>();
        tags.add(t1);
        tags.add(t2);
        Product product1 = Product.builder()
                .productName("macbook air m2")
                .productPrice(100000f)
                .stock(13)
                .seller(s)
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .tags(tags)
                .build();
        Product product2 = new Product();
        product2.setProductName("tttt");
        product2.setTags(tags);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        s.setProducts(products);
        productRepository.save(product1);
        productRepository.save(product2);
        sellerRepository.save(s);

        return productRepository.findAll();
    }

    @GetMapping("/t2")
    public Object test2() {
        Seller seller = sellerRepository.findById((long) 1).get();
        List<Product> products = seller.getProducts();

        //  Product product = productRepository.findById(10l).get();

        //Product p =products.get(0);
        //System.out.println(p.getDiscount());
        //System.out.println(p.getStock());
        //System.out.println(p.getSeller());
        //System.out.println(p.getProduct_id());
        //System.out.println(p.getProductPrice());
        //System.out.println(p.getProductName());
        return products;
    }

    @GetMapping("/t3")
    public Object test3(@RequestParam long tagId, @RequestParam String tagName) {
        ElasticTag tag = ElasticTag.builder()
                .tagId(tagId)
                .tagName(tagName)
                .build();
        ElasticTag tag1 = ElasticTag.builder()
                .tagId(tagId + 1)
                .tagName("iphone")
                .build();
        ElasticTag t = elasticTagRepository.save(tag);
        ElasticTag t1 = elasticTagRepository.save(tag1);

        List<ElasticTag> tags = new ArrayList<>();
        tags.add(t);
        tags.add(t1);
        ElasticProduct product = ElasticProduct.builder()
                .id(1)
                .product_name("iphone 2")
                .brand("apple")
                .tags(tags)
                .build();
        elasticProductRepository.save(product);
        return elasticProductRepository.findAll();
    }

    @GetMapping("/test4")
    public Object test4(@RequestParam String query) throws IOException {
        //elasticProductRepository.deleteAll();
        //dataTransferService.transferData();
//        System.out.println(query);
        cartRepository.deleteById(1l);
        return "elasticSearchService.search(query, 0, 200000, RED)";
//        return elasticProductRepository.findAll();
    }

    @GetMapping("/test5")
    public Object test5() {
        return elasticProductRepository.findAll();
    }
}
