package com.pranav.eCommerce.controller;

import com.pranav.eCommerce.model.Product;
import com.pranav.eCommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProduct()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/{productid}")
    public Product getProductById(@PathVariable Long productid)
    {
        return productService.getProductById(productid);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
    }
}
