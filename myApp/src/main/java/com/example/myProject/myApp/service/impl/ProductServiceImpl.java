
package com.example.myProject.myApp.service.impl;


import com.example.myProject.myApp.dto.ProductPatchDto;
import com.example.myProject.myApp.entity.Product;
import com.example.myProject.myApp.exception.NoSuchProductException;
import com.example.myProject.myApp.repository.ProductRepository;
import com.example.myProject.myApp.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Entering.. CLASS: {}, METHOD: createProduct, product: {}", this.getClass(), product);
        product.setStatus("A");
        return productRepository.save(product);
    }

    @Override
    public boolean isProductExist(String name) {
        log.info("Entering.. CLASS: {}, METHOD: isProductExist , product name: {}", this.getClass(), name);
        return productRepository.existsByName(name);
    }

    @Override
    public boolean isProductExist(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: isProductExist , product id: {}", this.getClass(), id);
        return productRepository.existsById(id);
    }

    @Override
    public Product getProduct(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: getProduct, id: {}", this.getClass(), id);
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductException("No such product category" +id));
    }

    @Override
    public Product updateProduct(Product product) {
        log.info("Entering.. CLASS: {}, METHOD: updateProduct, product: {}", this.getClass(), product);
        return productRepository.save(product);
    }

    @Override
    public Product patchProduct(Product product, ProductPatchDto productPatchDto) {
        log.info("Entering.. CLASS: {}, METHOD: patchProduct, id: {}", this.getClass(), product.getId());
        if (StringUtils.hasText(productPatchDto.getName())) {
            product.setName(productPatchDto.getName());
        }
        if (StringUtils.hasText(productPatchDto.getDescription())) {
            product.setDescription(productPatchDto.getDescription());
        }
        if (!ObjectUtils.isEmpty(productPatchDto.getPrice())) {
            product.setPrice(productPatchDto.getPrice());
        }
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Product product) {
        log.info("Entering.. CLASS: {}, METHOD: deleteProduct, id: {}", this.getClass(), product.getId());
        product.setStatus("D");
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByCategory(Long id) {
        log.info("Entering.. CLASS: {}, METHOD: getProductsByCategory, id: {}", this.getClass(), id);
        return productRepository.findByCategoryIdAndStatus(id, "A");
    }

    @Override
    public List<Product> getAllProductsByPriceGreaterOrEquals(Double price) {
        log.info("Entering.. CLASS: {}, METHOD: getAllPremiumProducts", this.getClass());
        return productRepository.findByPriceGreaterThanEqualAndStatus(price, "A");
    }

}
