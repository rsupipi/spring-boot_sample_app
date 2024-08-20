package com.example.myProject.myApp.service.impl;

import com.example.myProject.myApp.entity.ProductCategory;
import com.example.myProject.myApp.exception.NoSuchProductCategoryException;
import com.example.myProject.myApp.repository.ProductCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsProductCategoryExist() {
        Long categoryId = 1L;
        when(productCategoryRepository.existsById(categoryId)).thenReturn(true);
        boolean exists = productCategoryService.isProductCategoryExist(categoryId);
        assertTrue(exists);
        verify(productCategoryRepository).existsById(categoryId);
    }

    @Test
    void testIsProductCategoryExist_whenNotExist() {
        Long categoryId = 1L;
        when(productCategoryRepository.existsById(categoryId)).thenReturn(false);
        boolean exists = productCategoryService.isProductCategoryExist(categoryId);
        assertFalse(exists);
        verify(productCategoryRepository).existsById(categoryId);
    }

    @Test
    void testGetProductCategory() {
        Long categoryId = 1L;
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(categoryId);
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(productCategory));
        ProductCategory result = productCategoryService.getProductCategory(categoryId);
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        verify(productCategoryRepository).findById(categoryId);
    }

    @Test
    void testGetProductCategory_whenNotFound() {
        Long categoryId = 1L;
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        NoSuchProductCategoryException thrown = assertThrows(NoSuchProductCategoryException.class, () ->
                productCategoryService.getProductCategory(categoryId));
        assertEquals("No such product category: " + categoryId, thrown.getMessage());
        verify(productCategoryRepository).findById(categoryId);
    }
}