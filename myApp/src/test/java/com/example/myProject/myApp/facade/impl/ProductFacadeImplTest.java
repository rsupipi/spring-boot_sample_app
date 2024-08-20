package com.example.myProject.myApp.facade.impl;

import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.entity.Product;
import com.example.myProject.myApp.entity.ProductCategory;
import com.example.myProject.myApp.exception.NoSuchProductCategoryException;
import com.example.myProject.myApp.exception.ProductAlreadyExistsException;
import com.example.myProject.myApp.mapper.ProductMapper;
import com.example.myProject.myApp.service.ProductCategoryService;
import com.example.myProject.myApp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductFacadeImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductCategoryService productCategoryService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductFacadeImpl productFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setCategoryId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("New Product");

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);

        when(productService.isProductExist(Mockito.anyString())).thenReturn(false);
        when(productCategoryService.isProductCategoryExist(Mockito.anyLong())).thenReturn(true);
        when(productCategoryService.getProductCategory(Mockito.anyLong())).thenReturn(productCategory);
        when(productService.createProduct(Mockito.any())).thenReturn(product);

        ProductDto createdProduct = productFacade.createProduct(productDto);

        assertNotNull(createdProduct);
        assertEquals(productDto.getName(), createdProduct.getName());
        verify(productService).isProductExist(productDto.getName());
        verify(productCategoryService).isProductCategoryExist(productDto.getCategoryId());
    }

    @Test
    void testCreateProduct_ProductAlreadyExists() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Existing Product");
        productDto.setCategoryId(1L);

        when(productService.isProductExist(productDto.getName())).thenReturn(true);

        ProductAlreadyExistsException thrown = assertThrows(ProductAlreadyExistsException.class, () ->
                productFacade.createProduct(productDto)
        );
        assertEquals("Product already exists: " + productDto.getName(), thrown.getMessage());
    }

    @Test
    void testCreateProduct_NoSuchProductCategory() {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setCategoryId(1L);

        when(productService.isProductExist(productDto.getName())).thenReturn(false);
        when(productCategoryService.isProductCategoryExist(productDto.getCategoryId())).thenReturn(false);

        NoSuchProductCategoryException thrown = assertThrows(NoSuchProductCategoryException.class, () ->
                productFacade.createProduct(productDto)
        );
        assertEquals("No such product category: " + productDto.getCategoryId(), thrown.getMessage());
    }

    @Test
    void testUpdateProduct() {
        ProductCategory productCategory = new ProductCategory();
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(1L);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setCategory(productCategory);
        existingProduct.getCategory().setId(1L);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);

        when(productService.getProduct(1L)).thenReturn(existingProduct);
        when(productCategoryService.getProductCategory(productDto.getCategoryId())).thenReturn(productCategory);
        when(productService.updateProduct(Mockito.any())).thenReturn(updatedProduct);

        ProductDto result = productFacade.updateProduct(productDto, 1L);

        assertNotNull(result);
        verify(productService).getProduct(1L);

    }

    @Test
    void testPatchProduct() {
        ProductPatchDto productPatchDto = new ProductPatchDto();
        Product existingProduct = new Product();
        Product updatedProduct = new Product();

        when(productService.getProduct(1L)).thenReturn(existingProduct);
        when(productService.patchProduct(existingProduct, productPatchDto)).thenReturn(updatedProduct);

        ProductDto result = productFacade.patchProduct(productPatchDto, 1L);

        assertNotNull(result);
        verify(productService).getProduct(1L);
        verify(productService).patchProduct(existingProduct, productPatchDto);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setStatus("Deleted");

        when(productService.getProduct(1L)).thenReturn(product);
        when(productService.deleteProduct(product)).thenReturn(product);

        String result = productFacade.deleteProduct(1L);

        assertEquals("Success", result);
        verify(productService).getProduct(1L);
        verify(productService).deleteProduct(product);
    }

    @Test
    void testGetProductsByCategory() {
        Long categoryId = 1L;
        Product product = new Product();
        List<Product> products = List.of(product);
        ProductDto productDto = new ProductDto();

        when(productCategoryService.isProductCategoryExist(categoryId)).thenReturn(true);
        when(productService.getProductsByCategory(categoryId)).thenReturn(products);

        List<ProductDto> result = productFacade.getProductsByCategory(categoryId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productCategoryService).isProductCategoryExist(categoryId);
        verify(productService).getProductsByCategory(categoryId);
    }

    @Test
    void testGetProductsByCategory_NoSuchProductCategory() {
        Long categoryId = 1L;

        when(productCategoryService.isProductCategoryExist(categoryId)).thenReturn(false);

        NoSuchProductCategoryException thrown = assertThrows(NoSuchProductCategoryException.class, () ->
                productFacade.getProductsByCategory(categoryId)
        );
        assertEquals("No such product category: " + categoryId, thrown.getMessage());
    }

    @Test
    void testGetAllPremiumProducts() {
        Double premiumValue = 100.0;
        Product product = new Product();
        List<Product> premiumProducts = List.of(product);
        ProductDto productDto = new ProductDto();

        when(productService.getAllProductsByPriceGreaterOrEquals(premiumValue)).thenReturn(premiumProducts);

        List<ProductDto> result = productFacade.getAllPremiumProducts(premiumValue);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productService).getAllProductsByPriceGreaterOrEquals(premiumValue);
    }
}