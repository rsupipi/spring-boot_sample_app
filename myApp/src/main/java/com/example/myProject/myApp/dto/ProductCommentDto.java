package com.example.myProject.myApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ProductCommentDto {

    private Long id;

    @NotBlank(message = "Comment name cannot be Blank")
    private String comment;

    private Date CreatedAt;

}
