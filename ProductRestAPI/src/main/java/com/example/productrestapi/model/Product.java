package com.example.productrestapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productsA4")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;

}
