package com.example.myProject.myApp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.entity.Product;
import com.example.myProject.myApp.exception.NoSuchProductException;
import com.example.myProject.myApp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Product Name");
        product.setPrice(100.0);
        product.setStatus("A");
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("A", createdProduct.getStatus());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testIsProductExistByName() {
        when(productRepository.existsByName("Product Name")).thenReturn(true);

        boolean exists = productService.isProductExist("Product Name");

        assertTrue(exists);
        verify(productRepository, times(1)).existsByName("Product Name");
    }

    @Test
    public void testIsProductExistById() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean exists = productService.isProductExist(1L);

        assertTrue(exists);
        verify(productRepository, times(1)).existsById(1L);
    }

    @Test
    public void testGetProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProduct(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> productService.getProduct(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(product);

        assertNotNull(updatedProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testPatchProduct() {
        ProductPatchDto patchDto = new ProductPatchDto();
        patchDto.setName("Updated Name");

        when(productRepository.save(product)).thenReturn(product);

        Product patchedProduct = productService.patchProduct(product, patchDto);

        assertEquals("Updated Name", patchedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        product.setStatus("D");
        when(productRepository.save(product)).thenReturn(product);

        Product deletedProduct = productService.deleteProduct(product);

        assertEquals("D", deletedProduct.getStatus());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetProductsByCategory() {
        List<Product> products = List.of(product);
        when(productRepository.findByCategoryIdAndStatus(1L, "A")).thenReturn(products);

        List<Product> result = productService.getProductsByCategory(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByCategoryIdAndStatus(1L, "A");
    }

    @Test
    public void testGetAllProductsByPriceGreaterOrEquals() {
        List<Product> products = List.of(product);
        when(productRepository.findByPriceGreaterThanEqualAndStatus(100.0, "A")).thenReturn(products);

        List<Product> result = productService.getAllProductsByPriceGreaterOrEquals(100.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByPriceGreaterThanEqualAndStatus(100.0, "A");
    }

}