package com.example.myProject.myApp.facade.impl;

import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.entity.Product;
import com.example.myProject.myApp.entity.ProductCategory;
import com.example.myProject.myApp.exception.NoSuchProductCategoryException;
import com.example.myProject.myApp.exception.ProductAlreadyExistsException;
import com.example.myProject.myApp.facade.ProductFacade;
import com.example.myProject.myApp.mapper.ProductMapper;
import com.example.myProject.myApp.service.ProductCategoryService;
import com.example.myProject.myApp.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductFacadeImpl(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Entering.. CLASS: {}, METHOD: createProduct, Creating product: {}", this.getClass(), productDto);

        if (productService.isProductExist(productDto.getName())) {
            throw new ProductAlreadyExistsException("Product already exists: " + productDto.getName());
        }

        if (productDto.getCategoryId() == null || productDto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category id can not be is null or empty");
        }

        if (!productCategoryService.isProductCategoryExist(productDto.getCategoryId())) {
            throw new NoSuchProductCategoryException("No such product category: " + productDto.getCategoryId());
        }

        Product product = ProductMapper.dtoToEntity(productDto);
        ProductCategory productCategory = productCategoryService.getProductCategory(productDto.getCategoryId());
        product.setCategory(productCategory);
        product = productService.createProduct(product);
        ProductDto productDtoResponse = ProductMapper.EntityToDto(product);
        log.info("Exiting.. CLASS: {}, METHOD: createProduct, productId: {}", this.getClass(), productDtoResponse.getId());
        return productDtoResponse;
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        log.info("Entering.. CLASS: {}, METHOD: updateProduct, Creating product: {}", this.getClass(), productDto);

        Product previousProduct = productService.getProduct(id);

        if (productDto.getCategoryId() == null || productDto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }

        Product newproduct = ProductMapper.dtoToEntity(productDto);
        newproduct.setId(id);
        if (previousProduct.getCategory().getId().equals(productDto.getCategoryId())) {
            newproduct.setCategory(previousProduct.getCategory());
        } else {
            ProductCategory productCategory = productCategoryService.getProductCategory(productDto.getCategoryId());
            newproduct.setCategory(productCategory);
        }
        Product updatedProduct = productService.updateProduct(newproduct);
        ProductDto productDtoResponse = ProductMapper.EntityToDto(updatedProduct);

        log.info("Exiting.. CLASS: {}, METHOD: updateProduct, productId: {}", this.getClass(), productDtoResponse);
        return productDtoResponse;
    }

    @Override
    @Transactional
    public ProductDto patchProduct(ProductPatchDto productPatchDto, Long id) {
        log.info("Entering.. CLASS: {}, METHOD: patchProduct, Creating product: {}", this.getClass(), productPatchDto);

        Product product = productService.getProduct(id);
        Product updateProduct = productService.patchProduct(product, productPatchDto);
        ProductDto productDtoResponse = ProductMapper.EntityToDto(updateProduct);

        log.info("Exiting.. CLASS: {}, METHOD: patchProduct, productId: {}", this.getClass(), productDtoResponse);

        return productDtoResponse;
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: deleteProduct, id: {}", this.getClass(), id);

        Product product = productService.getProduct(id);
        Product deletedProduct = productService.deleteProduct(product);

        log.info("Exiting.. CLASS: {}, METHOD: deleteProduct, status: {}", this.getClass(), deletedProduct.getStatus());
        return "Success";
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: getProductsByCategory, id: {}", this.getClass(), id);

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Category id can not be is null or empty");
        }

        if (!productCategoryService.isProductCategoryExist(id)) {
            throw new NoSuchProductCategoryException("No such product category: " + id);
        }
        List<Product> products = productService.getProductsByCategory(id);

        return products.stream().map(ProductMapper::EntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllPremiumProducts(Double premiumValue) {
        log.info("Entering.. CLASS: {}, METHOD: getAllPremiumProducts", this.getClass());

        List<Product> premiumProducts = productService.getAllProductsByPriceGreaterOrEquals(premiumValue);
        return premiumProducts.stream().map(ProductMapper::EntityToDto).collect(Collectors.toList());
    }

}
