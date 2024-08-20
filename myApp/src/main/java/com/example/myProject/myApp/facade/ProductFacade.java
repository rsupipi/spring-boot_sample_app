package com.example.myProject.myApp.facade;

import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.dto.ProductPatchDto;

import java.util.List;

/**
 * This facade provides a simplified interface for managing Product related operations.
 */
public interface ProductFacade {

    /**
     * Create a product
     *
     * @param productDto the data transfer object containing user information
     * @return the created product
     */
    ProductDto createProduct(ProductDto productDto);

    /**
     * Update an existing product
     *
     * @param productDto update product
     * @param id         product id
     * @return updated product
     */
    ProductDto updateProduct(ProductDto productDto, Long id);

    /**
     * Patch an existing product
     *
     * @param productPatchDto update product
     * @param id              product id
     * @return updated product
     */
    ProductDto patchProduct(ProductPatchDto productPatchDto, Long id);

    /**
     * Delete the product
     *
     * @param id product id
     * @return status of the deletion
     */
    String deleteProduct(Long id);

    /**
     * Get product by category
     *
     * @param id
     * @return product list
     */
    List<ProductDto> getProductsByCategory(Long id);

    /**
     * Get all premium products
     *
     * @param premiumValue given premium value
     * @return product list
     */
    List<ProductDto> getAllPremiumProducts(Double premiumValue);

}
