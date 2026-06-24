package com.amri.productservice.repositories;

import com.amri.productservice.models.Product;
import com.amri.productservice.repositories.projections.ProductWithTitleAndPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Override
     Product save(Product product);

    @Override
    Product getById(Long aLong);


    //Native Query = SQL Query
    @Query(value = "select p.title, p.price from products p where p.title = :title and p.price = :price", nativeQuery = true)
    List<ProductWithTitleAndPrice> getProductTitleAndPriceSQL(String title, Double price);
}
