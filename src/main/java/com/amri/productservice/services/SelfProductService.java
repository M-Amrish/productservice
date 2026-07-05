package com.amri.productservice.services;

import com.amri.productservice.exceptions.ProductNotFoundException;
import com.amri.productservice.models.Category;
import com.amri.productservice.models.Product;
import com.amri.productservice.repositories.CategoryRepository;
import com.amri.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{
    public ProductRepository productRepository;
    public CategoryRepository categoryRepository;

    SelfProductService( ProductRepository productRepository,
                       CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        return productRepository.getById(productId);
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();

        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());

        if(optionalCategory.isEmpty()){
            // create
            category = categoryRepository.save(category);
        }else{
           category =  optionalCategory.get();
        }
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        categoryRepository.deleteById(id);
    }
}
