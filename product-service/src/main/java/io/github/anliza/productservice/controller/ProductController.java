package io.github.anliza.productservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anliza.productservice.model.GenericResponse;
import io.github.anliza.productservice.model.ProductCreateRequest;
import io.github.anliza.productservice.model.ProductCreateResponse;
import io.github.anliza.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/products")
@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public GenericResponse<List<ProductCreateResponse>> list() {
       List<ProductCreateResponse> pr = productService.findAll();
       GenericResponse<List<ProductCreateResponse>> resp = GenericResponse.<List<ProductCreateResponse>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }

    @GetMapping("/{productId}")
    public GenericResponse<ProductCreateResponse> findById(@PathVariable(name = "productId")  Integer productId) {
        ProductCreateResponse pr = productService.findById(productId);
        GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }

    @PostMapping
    public GenericResponse<ProductCreateResponse> createProduct(
            @RequestBody ProductCreateRequest productCreateRequest) {
                log.info("We received : {}",productCreateRequest);
        ProductCreateResponse pr = productService.createProduct(productCreateRequest);
        GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
        return resp;
    }
    
}
