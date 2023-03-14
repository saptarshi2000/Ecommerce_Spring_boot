package com.saptarshi.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_id;

    private String brand;
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private float productPrice;

//    private String description;

    private String color;

    private float discount;

    int stock;

//    @ColumnDefault("true")
//    @Column(columnDefinition = "boolean default true")
    private Boolean active;



    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<Tag> tags;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    @JsonIgnore //use @JsonIgnore problem is infinite json calls are going through your json call. to ignore that use this annotation on your ManytoOne relation.
    private Seller seller;
}
