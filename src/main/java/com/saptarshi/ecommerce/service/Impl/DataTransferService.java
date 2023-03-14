package com.saptarshi.ecommerce.service.Impl;

import com.saptarshi.ecommerce.model.*;
import com.saptarshi.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataTransferService {

    private final ProductRepository productRepository;

    private final ElasticProductRepository elasticProductRepository;

    private  final ElasticTagRepository elasticTagRepository;

    private final ConfigurationRepository configurationRepository;

    public void transferData(){
        Optional<Configuration> configuration = configurationRepository.findByName("last_update");

        Timestamp lastUpdate;
        List<Product> products;
        if(configuration.isPresent()){
            lastUpdate = Timestamp.valueOf(configuration.get().getValue());
            products = productRepository.findByUpdatedAtGreaterThan(lastUpdate);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Configuration c = configuration.get();
            c.setValue(timestamp.toString());
            configurationRepository.save(c);
            //System.out.println(products.size());
        }else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Configuration configuration1 = new Configuration();
            configuration1.setName("last_update");
            configuration1.setValue(timestamp.toString());
            configurationRepository.save(configuration1);
            products = productRepository.findAll();
        }


        for (Product product : products) {
            List<ElasticTag> tags = new ArrayList<>();
            for (Tag tag : product.getTags()) {
                ElasticTag elasticTag = ElasticTag.builder()
                        .tagId(tag.getTagId())
                        .tagName(tag.getTagName())
                        .build();
                elasticTagRepository.save(elasticTag);
                tags.add(elasticTag);
            }
            ElasticProduct eProduct = ElasticProduct.builder()
                    .brand(product.getBrand())
                    .price(product.getProductPrice())
                    .product_name(product.getProductName())
                    .color(product.getColor())
                    .id(product.getProduct_id())
                    .tags(tags)
                    .build();
            elasticProductRepository.save(eProduct);
        }


    }

}
