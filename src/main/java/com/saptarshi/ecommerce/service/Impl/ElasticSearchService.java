package com.saptarshi.ecommerce.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.saptarshi.ecommerce.model.ElasticProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final ElasticsearchClient client;

    public List<ElasticProduct> search(String query, int min, int max, String color) throws IOException {
        List<ElasticProduct> products = new ArrayList<>();
        Query q1 = MatchQuery.of(m -> m
                .field("brand")
                .query(query)
        )._toQuery();

        Query q2 = NestedQuery.of(n -> n
                .path("tags")
                .query(q -> q
                        .matchPhrase(m -> m
                                .field("tags.tagName")
                                .query(query)
                        )
                )
        )._toQuery();

        Query q3 = MatchPhraseQuery.of(f -> f
                .field("product_name")
                .query(query)
        )._toQuery();

        Query q4 = RangeQuery.of(r -> r
                .field("price")
                .gte(JsonData.of(min))
                .lte(JsonData.of(max))

        )._toQuery();

        Query q5;


//        if(!color.isEmpty()) {
//            q5 = TermQuery.of(t -> t
//                    .field("color")
//                    .value(color)
//            )._toQuery();
//            System.out.println("test");
//        } else {
//            q5 = null;
//        }

        if (!color.isEmpty()) {
            q5 = MatchQuery.of(t -> t
                    .field("color")
                    .query(color)
            )._toQuery();
           // System.out.println("test");
        } else {
            q5 = null;
        }

//        SearchResponse<ElasticProduct> response = client.search(s -> s
//                .index("products")
//                .query(q -> q
//                        .bool(b -> {
//                                    b
//                                    .must(q1)
//                                    .must(q2)
//                                    .must(q3);
////                                    .must(q4);
//                                    if(q5!=null){
//                                        b.must(q5);
//                                    }
//                                    return b;
//                                }
//
//                        )
//                ),ElasticProduct.class
//        );


        SearchResponse<ElasticProduct> response = client.search(s -> s
                        .index("products")
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .bool(b1 -> b1
                                                        .should(q1)
                                                        .should(q2)
                                                        .should(q3)
                                                )
                                        )
                                        .must(q4)
                                        .must(q5)
                                )
                        )
                , ElasticProduct.class

        );

        List<Hit<ElasticProduct>> hits = response.hits().hits();

        for (Hit<ElasticProduct> productHit : hits) {
            products.add(productHit.source());
        }

        return products;
    }
}
