
package com.example.myProject.myApp.service;

import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.entity.Product;

import java.util.List;

/**
 * Service class responsible for product-related operations.
 */
public interface ProductService {

    /**
     * Save a Product to the database
     *
     * @param product
     * @return saved product
     */
    Product createProduct(Product product);

    /**
     * Check availability of Product by productName
     *
     * @param name
     * @return availability
     */
    boolean isProductExist(String name);

    /**
     * Check availability of Product by productName
     *
     * @param id
     * @return availability
     */
    boolean isProductExist(Long id);

    /**
     * get product by id
     * @param id
     * @return the product
     */
    Product getProduct(Long id);

    /**
     * Update an existing product
     *
     * @param product update product
     * @return updated product
     */
    Product updateProduct(Product product);

    /**
     * Patch an existing product
     *
     * @param product update product
     * @return patched product
     */
    Product patchProduct(Product product, ProductPatchDto productPatchDto);

    Product deleteProduct(Product product);

    List<Product> getAllProductsByPriceGreaterOrEquals(Double price);

    List<Product> getProductsByCategory(Long id);
}