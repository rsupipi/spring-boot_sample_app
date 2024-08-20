
package com.example.myProject.myApp.service;


import com.example.myProject.myApp.entity.ProductCategory;

public interface ProductCategoryService {

    /**
     * Check availability of Product-Category
     * @param id
     * @return availability of the Product-Category
     */
    boolean isProductCategoryExist(Long id);

    /**
     * Finds a Product-Category by id
     * @param id
     * @return Product-Category
     */
    ProductCategory getProductCategory(Long id);

}