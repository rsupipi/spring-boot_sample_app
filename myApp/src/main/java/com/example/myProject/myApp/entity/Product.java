package com.example.myProject.myApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "product")
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String description;

    private double price;

    private String status;

    private Date LaunchDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductComment> comments;


}
