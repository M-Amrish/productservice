package com.amri.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="products")
public class Product extends BaseModel {
    private String title;
    private String description;
    private String imageUrl;
    private double price;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    private Category category;

//        1         1
//    product === category ===> M:1
//       M           1
}
