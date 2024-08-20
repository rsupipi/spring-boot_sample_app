package com.example.myProject.myApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "product_comment")
@DynamicUpdate
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @Column(name = "created_at")
    private Date CreatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
