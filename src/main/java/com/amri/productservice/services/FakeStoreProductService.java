package com.amri.productservice.services;

import com.amri.productservice.dtos.FakeStoreProductDto;
import com.amri.productservice.exceptions.ProductNotFoundException;
import com.amri.productservice.models.Category;
import com.amri.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    public RedisTemplate<String, Objects> redisTemplate;

    FakeStoreProductService(RestTemplate restTemplate,
                            RedisTemplate redisTemplate){
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        // Make a API call to FakeStore and get the product with the given Id.
        // https://fakestoreapi.com/products/10

        //First check in the REDIS if the product with the given id is present or not.
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);

        //Cache HIT
        if (product != null) {
            return product;
        }

        //Cache MISS
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product with id: " + productId + " doesn't exist.");
        }

        product = convertToProduct(fakeStoreProductDto);

        //Store this product in the cache.
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);

        //Convert FakeStoreProductDto object into a Product object.
        return product;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        String url ="https://fakestoreapi.com/products/";
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(
            url, FakeStoreProductDto[].class
        );
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto i : fakeStoreProductDtos){
            products.add(convertToProduct(i));
        }
        return new PageImpl<>(products);
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
