package com.saptarshi.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticProduct {

    @Id
    private long id;

    private String brand;

    private String product_name;

    //TODO implement description and color codes in service class and data transform class

//    private String description;

    private String color;

    private float price;

    @Field(type = FieldType.Nested)
    private List<ElasticTag> tags;

}
