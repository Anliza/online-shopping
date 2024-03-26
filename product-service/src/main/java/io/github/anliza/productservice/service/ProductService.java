package io.github.anliza.productservice.service;

import java.util.List;

import io.github.anliza.productservice.model.ProductCreateRequest;
import io.github.anliza.productservice.model.ProductCreateResponse;

public interface ProductService {
    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    List<ProductCreateResponse> findAll();

    ProductCreateResponse findById(Integer productId);
}
