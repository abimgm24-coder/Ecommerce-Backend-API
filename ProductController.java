package com.abirami.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
        return "Product deleted successfully with id: " + id;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return repository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    updatedProduct.setId(id);
                    return repository.save(updatedProduct);
                });
    }
}


