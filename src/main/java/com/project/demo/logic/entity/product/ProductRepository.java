package com.project.demo.logic.entity.product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer>{
    Optional<Product> findByName(Product name);
}
