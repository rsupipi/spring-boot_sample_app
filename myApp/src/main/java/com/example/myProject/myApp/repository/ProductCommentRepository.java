package com.example.myProject.myApp.repository;

import com.example.myProject.myApp.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
}
