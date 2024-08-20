package com.example.myProject.myApp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@ToString
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name cannot be Blank")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "Price must be a valid amount")
    private double price;

    private String status;

    private Date LaunchDate;

    private Set<ProductCommentDto> comments;

    private Long categoryId;

}
