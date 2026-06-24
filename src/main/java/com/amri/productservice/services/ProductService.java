package com.amri.productservice.services;

import com.amri.productservice.exceptions.ProductNotFoundException;
import com.amri.productservice.models.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId)throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product replaceProduct(Long id, Product product);

    void deleteProduct(Long id);
}
