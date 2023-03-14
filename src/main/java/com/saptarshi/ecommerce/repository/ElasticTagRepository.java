package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.ElasticTag;
import com.saptarshi.ecommerce.model.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElasticTagRepository extends ElasticsearchRepository<ElasticTag,Long> {
}
