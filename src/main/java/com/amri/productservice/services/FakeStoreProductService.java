package com.amri.productservice.services;

import com.amri.productservice.dtos.FakeStoreProductDto;
import com.amri.productservice.exceptions.ProductNotFoundException;
import com.amri.productservice.models.Category;
import com.amri.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long productId)throws ProductNotFoundException{
        String url ="https://fakestoreapi.com/products/"+productId;

        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject(url, FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product with id: " + productId + " doesn't exist.");
        }

        return convertToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        String url ="https://fakestoreapi.com/products/";
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(
            url, FakeStoreProductDto[].class
        );
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto i : fakeStoreProductDtos){
            products.add(convertToProduct(i));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    private Product convertToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());


        product.setCategory(category);

        return product;
    }
}
