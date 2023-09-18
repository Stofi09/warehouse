package com.warehouse.warehouse.controller.product;

import com.warehouse.warehouse.model.Product;
import com.warehouse.warehouse.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/v1")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAll(){
        return this.productService.getAllProducts();
    }
}
