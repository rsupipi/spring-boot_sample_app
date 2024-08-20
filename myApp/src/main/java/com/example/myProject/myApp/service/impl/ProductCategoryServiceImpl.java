
package com.example.myProject.myApp.service.impl;


import com.example.myProject.myApp.entity.ProductCategory;
import com.example.myProject.myApp.exception.NoSuchProductCategoryException;
import com.example.myProject.myApp.repository.ProductCategoryRepository;
import com.example.myProject.myApp.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public boolean isProductCategoryExist(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: {}, id: {}", this.getClass(), "isProductCategoryExist", id);
        return productCategoryRepository.existsById(id);
    }

    @Override
    public ProductCategory getProductCategory(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: {}, category_id: {}", this.getClass(), "getProductCategory", id);
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductCategoryException("No such product category: " + id));
    }
}
