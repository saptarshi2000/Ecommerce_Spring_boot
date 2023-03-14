//package com.saptarshi.ecommerce.service.Impl;
//
//import com.saptarshi.ecommerce.model.ElasticProduct;
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ElasticSearchService_ {
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
////    @Autowired
////    private ElasticsearchOperations elasticsearchOperations;
////
////    @Autowired
////    private ElasticProductRepository elasticProductRepository;
////
//    public Object search(String query,Double minPrice, Double maxPrice) throws IOException {
//
//        //must == and // should==or
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("brand",query))
//                .should(QueryBuilders.matchQuery("product_name",query))
//                .should(QueryBuilders.nestedQuery("tags",
//                        QueryBuilders.matchQuery("tags.tagName",query), ScoreMode.None));
//
////        QueryBuilder queryBuilder1 = QueryBuilders.
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(queryBuilder);
//
//        SearchRequest searchRequest = new SearchRequest("products");
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//
//        SearchHits elasticProductSearchHit = searchResponse.getHits();
//
//        List<ElasticProduct> elasticProducts = new ArrayList<>();
//
////        for(SearchHit hit :elasticProductSearchHit.getHits()){
////            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
////            ElasticProduct product = new ElasticProduct();
////            product.setBrand((String) sourceAsMap.get("brand"));
////            elasticProducts.add(product);
////        }
////        return  elasticProducts;
//        return elasticProductSearchHit;
//    }
//}
