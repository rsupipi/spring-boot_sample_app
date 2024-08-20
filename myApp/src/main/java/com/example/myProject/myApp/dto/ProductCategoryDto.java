package com.example.myProject.myApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCategoryDto {

    private Long id;

    @NotBlank(message = "Product Category name cannot be Blank")
    private String name;
}
