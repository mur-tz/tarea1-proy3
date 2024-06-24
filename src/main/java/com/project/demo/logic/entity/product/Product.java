package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Table(name = "product")
@Entity
public class Product {

    //id, nombre, descripción, precio, cantidad en stock y Categoría
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer qtyInStock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(Integer qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
