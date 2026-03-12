package com.javaexpress.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String name;
    
    //@Lob
    @Column(length = 100000) // optional: set your desired length
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String status;
    private String brand;

    @Lob
    private byte[] image;

    private LocalDateTime createdAt = LocalDateTime.now();
}

