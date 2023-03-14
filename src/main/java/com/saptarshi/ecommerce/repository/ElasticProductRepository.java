package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct,Long> {
}
