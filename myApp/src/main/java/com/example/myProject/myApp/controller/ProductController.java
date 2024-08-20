package com.example.myProject.myApp.controller;

import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.facade.ProductFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Product-related HTTP requests.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    /**
     * Creates a new product
     *
     * @param productDto the product details
     * @return the response entity containing the created product
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Validated @RequestBody() ProductDto productDto) {
        return new ResponseEntity<>(productFacade.createProduct(productDto), HttpStatus.OK);
    }

    /**
     * Update a product
     *
     * @param id
     * @param productDto
     * @return the response entity containing the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> UpdateProduct(@PathVariable Long id,
                                                    @Validated @RequestBody() ProductDto productDto) {
        return new ResponseEntity<>(productFacade.updateProduct(productDto, id), HttpStatus.OK);
    }

    /**
     * Partial update a product a new product
     *
     * @param id
     * @param productPatchDto the product details
     * @return the response entity containing the updated product
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> PartialUpdateProduct(@PathVariable Long id,
                                                           @Validated @RequestBody() ProductPatchDto productPatchDto) {
        return new ResponseEntity<>(productFacade.patchProduct(productPatchDto, id), HttpStatus.OK);
    }

    /**
     * Delete a product
     *
     * @param id the product id
     * @return the response entity containing the created product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productFacade.deleteProduct(id), HttpStatus.OK);
    }

    /**
     * Retrieve all the products in a given product category
     *
     * @param id category id
     * @return product list
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long id) {
        List<ProductDto> products = productFacade.getProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Retrieve all premium products
     *
     * @return product list
     */
    @GetMapping("/premium")
    public ResponseEntity<List<ProductDto>> getPremiumProducts() {
        Double premiumValue = 500.0;
        List<ProductDto> products = productFacade.getAllPremiumProducts(premiumValue);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
