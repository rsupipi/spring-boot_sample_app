package com.example.myProject.myApp.mapper;

import com.example.myProject.myApp.dto.ProductCategoryDto;
import com.example.myProject.myApp.dto.ProductCommentDto;
import com.example.myProject.myApp.dto.ProductDto;
import com.example.myProject.myApp.entity.Product;
import com.example.myProject.myApp.entity.ProductCategory;
import com.example.myProject.myApp.entity.ProductComment;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

public class ProductMapper {

    public static Product dtoToEntity(ProductDto dto) {

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStatus(dto.getStatus());
        product.setLaunchDate(dto.getLaunchDate());

        if (!CollectionUtils.isEmpty(dto.getComments())){
            Set<ProductComment> productComments = new HashSet<>();
            dto.getComments().forEach(comment ->{
                ProductComment productComment = new ProductComment();
                productComment.setId(comment.getId());
                productComment.setComment(comment.getComment());
                productComment.setCreatedAt(comment.getCreatedAt());

                productComments.add(productComment);
            });
            product.setComments(productComments);
        }
        return product;
    }

    public static ProductDto EntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStatus(product.getStatus());
        productDto.setLaunchDate(product.getLaunchDate());

        if (product.getCategory() != null){
            productDto.setCategoryId(product.getCategory().getId());
        }

        if (product.getComments() != null && !product.getComments().isEmpty()){
            Set<ProductCommentDto> productCommentsDtos = new HashSet<>();
            product.getComments().forEach(comment ->{
                ProductCommentDto productCommentDto = new ProductCommentDto();
                productCommentDto.setId(comment.getId());
                productCommentDto.setComment(comment.getComment());
                productCommentDto.setCreatedAt(comment.getCreatedAt());

                productCommentsDtos.add(productCommentDto);
            });
            productDto.setComments(productCommentsDtos);
        }
        return productDto;
    }

}
