package com.amri.productservice;

import com.amri.productservice.repositories.ProductRepository;
import com.amri.productservice.repositories.projections.ProductWithTitleAndPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductserviceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
        List<ProductWithTitleAndPrice> products = productRepository.getProductTitleAndPriceSQL("Iphone 15",20000.0);

        for (ProductWithTitleAndPrice product : products) {
            System.out.println(product.getTitle() + " -> " + product.getPrice());
        }

    }

}

