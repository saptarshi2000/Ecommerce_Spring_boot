package com.saptarshi.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seller_id;

    @Column(name = "seller_name")
    private String sellerName;
    private long registration;

    @Column(name = "user_email")
    private String  userEmail;

    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JsonIgnore
    List<Product> products;
}
