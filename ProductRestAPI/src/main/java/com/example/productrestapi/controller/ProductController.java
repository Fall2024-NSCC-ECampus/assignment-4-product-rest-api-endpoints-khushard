package com.example.productrestapi.controller;

import com.example.productrestapi.model.Product;
import com.example.productrestapi.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * REST Controller for managing all Product operations.
 * Provides API endpoints for CRUD operations.
 * */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    /**
     * Gets all products from the database.
     * @return ResponseEntity that contains either:
     *      -204 No Content if no products are in the system
     *      -200 OK with a list of all products in the system after determining if products exist.
     * */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


    /**
     * Gets a product based on the ID of the Product.
     * @param id The ID of the product to be retrieved.
     * @return ResponseEntity containing either:
     *      - 200 OK with the found product
     *      - 404 Not Found if the product doesn't exist in database.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new product in the database.
     * @param product The product object to be created.
     * @return ResponseEntity containing:
     *      - 201 Created with the product that was created.
     * */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productRepo.save(product);
        return ResponseEntity.status(201).body(newProduct);

    }

    /**
     * Updates an existing product in the database.
     * @param id The ID of the product to update
     * @param product The product that is to be updated.
     * @return ResponseEntity containing either:
     *      - 200 OK with the updated product.
     *      - 404 Not Found if the product doesn't exist.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        if (!productRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        return ResponseEntity.ok(productRepo.save(product));
    }

    /**
     * Deletes a product from the database
     * @param id The ID of the product to be deleted.
     * @return ResponseEntity containing either:
     *      - 204 No Content if deleted successfully
     *      - 404 Not Found if product doesn't exist
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        if (!productRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
